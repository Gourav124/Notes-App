package com.example.practiceapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.practiceapp.components.NotesTopBar
import androidx.compose.ui.graphics.Color
import com.example.practiceapp.ui.theme.NotesViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.practiceapp.database.UserModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesEditScreen(
    onNavigateBack: () -> Unit,
    viewModel: NotesViewModel,
    noteToEdit: UserModel? = null
) {
    var title by remember { mutableStateOf(noteToEdit?.title ?: "") }
    var notesContent by remember { mutableStateOf(noteToEdit?.content ?: "") }
    var showError by remember { mutableStateOf(false) }
    var showExitDialog by remember { mutableStateOf(false) }

    fun handleBackPress() {
        if (title != (noteToEdit?.title ?: "") || notesContent != (noteToEdit?.content ?: "")) {
            showExitDialog = true
        } else {
            onNavigateBack()
        }
    }

    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            title = { Text("Unsaved Changes") },
            text = { Text("Do you want to save your changes before leaving?") },
            confirmButton = {
                Button(
                    onClick = {
                        if (title.isNotBlank() && notesContent.isNotBlank()) {
                            if (noteToEdit != null) {
                                viewModel.updateNote(
                                    UserModel(
                                        id = noteToEdit.id,
                                        title = title,
                                        content = notesContent
                                    )
                                )
                            } else {
                                viewModel.addNotes(title, notesContent)
                            }
                            onNavigateBack()
                        } else {
                            showError = true
                            showExitDialog = false
                        }
                    }
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showExitDialog = false
                        onNavigateBack()
                    }
                ) {
                    Text("Discard")
                }
            }
        )
    }

    Scaffold(
        modifier = Modifier.padding(start = 10.dp, top = 16.dp),
        topBar = {
            NotesTopBar(
                title = if (noteToEdit != null) " Edit Note" else " Add Note",
                onBackClick = { handleBackPress() },
                actions = {
                    Card(
                        onClick = {
                            if (title.isNotBlank() && notesContent.isNotBlank()) {
                                if (noteToEdit != null) {
                                    viewModel.updateNote(
                                        UserModel(
                                            id = noteToEdit.id,
                                            title = title,
                                            content = notesContent
                                        )
                                    )
                                } else {
                                    viewModel.addNotes(title, notesContent)
                                }
                                onNavigateBack()
                            } else {
                                showError = true
                            }
                        },
                        modifier = Modifier.padding(end = 20.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = "Save",
                            tint = MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextField(
                value = title,
                onValueChange = { 
                    title = it
                    showError = false
                },
                placeholder = { Text("Title", style = MaterialTheme.typography.headlineMedium) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.headlineMedium,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent
                )
            )

            TextField(
                value = notesContent,
                onValueChange = { 
                    notesContent = it
                    showError = false
                },
                placeholder = { Text("Type something...", style = MaterialTheme.typography.bodyLarge) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.bodyLarge,
                minLines = 5,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent
                )
            )

            if (showError) {
                Text(
                    text = "Please enter both title and content",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
    }
}




