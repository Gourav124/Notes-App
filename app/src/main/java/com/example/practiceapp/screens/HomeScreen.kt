package com.example.practiceapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.practiceapp.R
import com.example.practiceapp.components.NotesItem
import com.example.practiceapp.components.NotesTopBar
import com.example.practiceapp.database.UserModel
import com.example.practiceapp.ui.theme.NotesViewModel

@Composable
fun HomeScreen(
    onSearchNotes: () -> Unit,
    onAddNotes: () -> Unit,
    onSeeInfo: () -> Unit,
    onEditNote: (UserModel) -> Unit,
    viewModel: NotesViewModel
) {
    val notesList by viewModel.notesList.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, end = 15.dp),
        topBar = {
            NotesTopBar(
                title = "Notes",
                actions = {
                    Card(
                        onClick = (onSearchNotes),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "search",
                            tint = MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    Card(
                        onClick = (onSeeInfo),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "info",
                            tint = MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = (onAddNotes),
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(top = 16.dp, start = 15.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (notesList.isNullOrEmpty()) {
                Image(
                    painter = painterResource(id = R.drawable.rafiki),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(top = 40.dp)
                        .size(400.dp)
                )
                Text(
                    text = "Create your first note!",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(vertical = 8.dp)
                ) {
                    itemsIndexed(notesList!!) { index: Int, item: UserModel ->
                        NotesItem(
                            item = item,
                            onDelete = {
                                viewModel.deleteNote(item)
                            },
                            onNoteClick = {
                                onEditNote(item)
                            },
                            index = index
                        )
                    }
                }
            }
        }
    }
}