package com.example.activites_journal.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.activites_journal.data.AppDao
import com.example.activites_journal.data.DailyJournalEntity
import com.example.activites_journal.utils.SessionManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
class JournalViewModel(
    private val appDao: AppDao,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private fun getTodayDateString() = dateFormat.format(Date())

    private val _userId = sessionManager.userId.filterNotNull()

    private val _selectedDate = MutableStateFlow(getTodayDateString())
    val selectedDate: StateFlow<String> = _selectedDate.asStateFlow()

    val journalEntry: StateFlow<DailyJournalEntity?> = combine(_userId, _selectedDate) { userId, date ->
        appDao.getJournalForDate(userId.toLong(), date)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val history: StateFlow<List<DailyJournalEntity>> = _userId.flatMapLatest { userId ->
        appDao.getJournalsForUser(userId.toLong())
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun upsertJournal(moodScale: Int, reflectionText: String) {
        viewModelScope.launch {
            val userId = sessionManager.userId.firstOrNull() ?: return@launch
            val journal = DailyJournalEntity(
                userId = userId.toLong(),
                date = _selectedDate.value,
                moodScale = moodScale,
                reflectionText = reflectionText
            )
            appDao.upsertDailyJournal(journal)
        }
    }

    fun getJournalByDate(date: String) {
        _selectedDate.value = date
    }
}
