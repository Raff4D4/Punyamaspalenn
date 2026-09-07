package com.example.activites_journal

import android.app.Application
import androidx.room.Room
import com.example.activites_journal.data.AppDatabase
import com.example.activites_journal.utils.SessionManager

class HabitJournalApp : Application() {
    val database by lazy {
        Room.databaseBuilder(this, AppDatabase::class.java, "habit_journal_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    val sessionManager by lazy {
        SessionManager(this)
    }
}
