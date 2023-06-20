package com.example.task2kotlin.Room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Database
import androidx.room.Room
import com.example.task2kotlin.CommonKeys
import com.example.task2kotlin.R
import kotlinx.coroutines.launch

class NoteViewModel (application: Application) : AndroidViewModel(application) {
    fun insertNoteText(text: String) {
        viewModelScope.launch {
            AppDatabase.getInstance(getApplication()).noteDao().insert(Note(text = text))
        }
    }

    fun getAllNotes(): LiveData<List<Note>> {
        return AppDatabase.getInstance(getApplication()).noteDao().getAllNotes()
    }
}

