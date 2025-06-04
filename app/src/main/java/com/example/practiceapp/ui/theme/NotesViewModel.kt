package com.example.practiceapp.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practiceapp.database.MainApplication
import com.example.practiceapp.database.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch

class NotesViewModel : ViewModel() {
    private val notesDao = MainApplication.notesDatabase.getNotesDao()
    private val _notesList = MutableStateFlow<List<UserModel>>(emptyList())
    val notesList: StateFlow<List<UserModel>> = notesDao.getAllNotes()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        _notesList.value = listOf(
            UserModel(title = "Meeting Notes", content = "Discuss project timeline"),
            UserModel(title = "Shopping List", content = "Milk, Eggs, Bread"),
            UserModel(title = "Ideas", content = "App features to implement")
        )
    }

    fun searchNotes(query: String): StateFlow<List<UserModel>> {
        _searchQuery.value = query
        return notesDao.searchNotes(query)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )
    }

    fun getOriginalIndex(note: UserModel): Int {
        return notesList.value.indexOfFirst { it.id == note.id }
    }

    fun addNotes(title: String, content: String) {
        viewModelScope.launch(Dispatchers.IO) {
            notesDao.addNotes(UserModel(
                title = title,
                content = content
            ))
        }
    }

    fun addNote(note: UserModel) {
        val currentList = _notesList.value.toMutableList()
        currentList.add(note)
        _notesList.value = currentList
    }

    fun updateNote(note: UserModel) {
        viewModelScope.launch(Dispatchers.IO) {
            notesDao.addNotes(note) // Using addNotes with REPLACE strategy
        }
    }

    fun deleteNote(note: UserModel) {
        viewModelScope.launch(Dispatchers.IO) {
            notesDao.deleteNotes(note)
        }
    }
}