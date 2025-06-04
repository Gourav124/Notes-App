package com.example.practiceapp.database

import android.app.Application
import androidx.room.Room

class MainApplication : Application() {
    companion object {
        lateinit var notesDatabase: NotesDatabase
            private set
    }

    override fun onCreate() {
        super.onCreate()
        notesDatabase = Room.databaseBuilder(
            applicationContext,
            NotesDatabase::class.java,
            "notes_database"
        ).build()
    }
}