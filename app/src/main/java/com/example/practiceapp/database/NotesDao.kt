package com.example.practiceapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {
    @Query("SELECT * FROM notes_table")
    fun getAllNotes(): Flow<List<UserModel>>

    @Query("SELECT * FROM notes_table WHERE title LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%'")
    fun searchNotes(query: String): Flow<List<UserModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNotes(note: UserModel)

    @Delete
    suspend fun deleteNotes(note: UserModel)
}