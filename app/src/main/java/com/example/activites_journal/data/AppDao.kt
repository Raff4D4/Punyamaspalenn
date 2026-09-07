package com.example.activites_journal.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    // User
    @Insert
    suspend fun registerUser(user: UserEntity): Long

    @Query("SELECT * FROM users WHERE username = :username AND password = :password LIMIT 1")
    suspend fun loginUser(username: String, password: String): UserEntity?

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: Long): UserEntity?

    // Habit
    @Insert
    suspend fun insertHabit(habit: HabitEntity): Long

    @Update
    suspend fun updateHabit(habit: HabitEntity)

    @Delete
    suspend fun deleteHabit(habit: HabitEntity)

    @Query("SELECT * FROM habits WHERE userId = :userId")
    fun getHabitsForUser(userId: Long): Flow<List<HabitEntity>>

    // Habit Log
    @Insert
    suspend fun insertHabitLog(log: HabitLogEntity): Long

    @Delete
    suspend fun deleteHabitLog(log: HabitLogEntity)

    @Query("SELECT * FROM habit_logs WHERE habitId = :habitId")
    fun getLogsForHabit(habitId: Long): Flow<List<HabitLogEntity>>

    @Query("SELECT habit_logs.* FROM habit_logs INNER JOIN habits ON habit_logs.habitId = habits.id WHERE habits.userId = :userId AND habit_logs.executionDate = :date")
    fun getLogsForUserByDate(userId: Long, date: String): Flow<List<HabitLogEntity>>

    @Query("SELECT habit_logs.* FROM habit_logs INNER JOIN habits ON habit_logs.habitId = habits.id WHERE habits.userId = :userId")
    fun getAllLogsForUser(userId: Long): Flow<List<HabitLogEntity>>

    // Daily Journal
    @Upsert
    suspend fun upsertDailyJournal(journal: DailyJournalEntity)

    @Query("SELECT * FROM daily_journals WHERE userId = :userId AND date = :date LIMIT 1")
    suspend fun getJournalForDate(userId: Long, date: String): DailyJournalEntity?

    @Query("SELECT * FROM daily_journals WHERE userId = :userId ORDER BY date DESC")
    fun getJournalsForUser(userId: Long): Flow<List<DailyJournalEntity>>

    // Reset
    @Query("DELETE FROM users")
    suspend fun clearAllUsers()

    @Query("DELETE FROM habits")
    suspend fun clearAllHabits()
}
