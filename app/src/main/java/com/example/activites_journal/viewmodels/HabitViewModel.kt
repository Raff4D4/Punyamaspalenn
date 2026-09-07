package com.example.activites_journal.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.activites_journal.data.AppDao
import com.example.activites_journal.data.HabitEntity
import com.example.activites_journal.data.HabitLogEntity
import com.example.activites_journal.utils.SessionManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

enum class HabitSortOrder {
    NAME_ASC, NAME_DESC, STREAK
}

data class HabitWithStreak(
    val habit: HabitEntity,
    val streak: Int,
    val isCompletedToday: Boolean
)

@OptIn(ExperimentalCoroutinesApi::class)
class HabitViewModel(
    private val appDao: AppDao,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _sortOrder = MutableStateFlow(HabitSortOrder.NAME_ASC)
    val sortOrder: StateFlow<HabitSortOrder> = _sortOrder.asStateFlow()

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private fun getTodayDateString() = dateFormat.format(Date())

    private val _userId = sessionManager.userId.filterNotNull()

    private val allLogs: StateFlow<List<HabitLogEntity>> = _userId.flatMapLatest { userId ->
        appDao.getAllLogsForUser(userId.toLong())
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val habits: StateFlow<List<HabitWithStreak>> = combine(
        _userId.flatMapLatest { userId -> appDao.getHabitsForUser(userId.toLong()) },
        allLogs,
        _sortOrder
    ) { habits, logs, order ->
        val today = getTodayDateString()
        val habitsList = habits.map { habit ->
            val habitLogs = logs.filter { it.habitId == habit.id }
            HabitWithStreak(
                habit = habit,
                streak = calculateStreak(habitLogs),
                isCompletedToday = habitLogs.any { it.executionDate == today }
            )
        }
        
        when (order) {
            HabitSortOrder.NAME_ASC -> habitsList.sortedBy { it.habit.name }
            HabitSortOrder.NAME_DESC -> habitsList.sortedByDescending { it.habit.name }
            HabitSortOrder.STREAK -> habitsList.sortedByDescending { it.streak }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val todayLogs: StateFlow<List<HabitLogEntity>> = _userId.flatMapLatest { userId ->
        appDao.getLogsForUserByDate(userId.toLong(), getTodayDateString())
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addHabit(name: String, targetFrequency: Int) {
        viewModelScope.launch {
            val userId = sessionManager.userId.firstOrNull() ?: return@launch
            appDao.insertHabit(HabitEntity(userId = userId.toLong(), name = name, targetFrequency = targetFrequency))
        }
    }

    fun logHabit(habitId: Long) {
        viewModelScope.launch {
            appDao.insertHabitLog(HabitLogEntity(habitId = habitId, executionDate = getTodayDateString()))
        }
    }

    private fun calculateStreak(logs: List<HabitLogEntity>): Int {
        if (logs.isEmpty()) return 0
        
        val sortedDates = logs.map { it.executionDate }.distinct().sortedDescending()
        val today = getTodayDateString()
        
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -1)
        val yesterday = dateFormat.format(calendar.time)
        
        if (sortedDates.first() != today && sortedDates.first() != yesterday) {
            return 0
        }
        
        var streak = 0
        var currentDate = if (sortedDates.first() == today) today else yesterday
        
        val datesSet = sortedDates.toSet()
        
        while (datesSet.contains(currentDate)) {
            streak++
            val date = dateFormat.parse(currentDate)!!
            calendar.time = date
            calendar.add(Calendar.DAY_OF_YEAR, -1)
            currentDate = dateFormat.format(calendar.time)
        }
        
        return streak
    }

    fun setSortOrder(order: HabitSortOrder) {
        _sortOrder.value = order
    }
}
