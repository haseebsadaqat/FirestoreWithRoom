package com.example.task2kotlin.Room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
@Dao
interface NoteDao {

    @Insert
    suspend fun insert(note: Note)

    @Query("SELECT * FROM note")
    fun getAllNotes(): LiveData<List<Note>>
}