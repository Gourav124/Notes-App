package com.example.practiceapp.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserModel::class], version = 1)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun getNotesDao(): NotesDao
}