package com.example.practiceapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.unit.dp
import com.example.practiceapp.database.UserModel
import com.example.practiceapp.ui.theme.NotesViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.practiceapp.components.NotesTopBar

private val lightThemeColors = listOf(
    Color(0xFF5890BB), // Blue
    Color(0xFFB46DBE), // Purple
    Color(0xFF5FAF66), // Green
    Color(0xFFD9AE6A), // Orange
    Color(0xFFB96581), // Pink
    Color(0xFF5EAAB4), // Cyan
    Color(0xFFA6D571), // Lime
    Color(0xFFC26B78), // Red
    Color(0xFF926EC9), // Deep Purple
    Color(0xFF6A78C9)  // Indigo
)

private val darkThemeColors = listOf(
    Color(0xFF7BA8D1), // Lighter Blue
    Color(0xFFC88ED2), // Lighter Purple
    Color(0xFF7FC285), // Lighter Green
    Color(0xFFE3C08A), // Lighter Orange
    Color(0xFFD18BA1), // Lighter Pink
    Color(0xFF7ECAD3), // Lighter Cyan
    Color(0xFFC0E391), // Lighter Lime
    Color(0xFFD48B98), // Lighter Red
    Color(0xFFB28ED9), // Lighter Deep Purple
    Color(0xFF8A98D9)  // Lighter Indigo
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onNavigateBack: () -> Unit,
    viewModel: NotesViewModel
) {
    var searchQuery by remember { mutableStateOf("") }
    val searchResults by viewModel.searchNotes(searchQuery).collectAsStateWithLifecycle(initialValue = emptyList())
    val isDarkTheme = MaterialTheme.colorScheme.background.luminance() < 0.5f
    val cardColors = if (isDarkTheme) darkThemeColors else lightThemeColors

    Scaffold(
        modifier = Modifier.padding(start = 10.dp, top = 16.dp),
        topBar = {
            NotesTopBar(
                title = " Search Notes",
                onBackClick = onNavigateBack
//                actions = {
//                    IconButton(onClick = onNavigateBack) {
//                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
//                    }
//                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Search notes...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Default.Clear, contentDescription = "Clear")
                        }
                    }
                },
                singleLine = true
            )

            if (searchQuery.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            modifier = Modifier.size(48.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Type to search notes",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(searchResults) { note ->
                        val originalIndex = viewModel.getOriginalIndex(note)
                        val cardColor = cardColors[originalIndex % cardColors.size]
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = cardColor
                            ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 4.dp
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = note.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = if (isDarkTheme) Color.White else Color.Black
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = note.content,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = if (isDarkTheme) Color.White else Color.Black
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}