package com.example.practiceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.practiceapp.database.UserModel
import com.example.practiceapp.navigation.Screen
import com.example.practiceapp.screens.HomeScreen
import com.example.practiceapp.screens.InfoScreen
import com.example.practiceapp.screens.NotesEditScreen
import com.example.practiceapp.screens.SearchScreen
import com.example.practiceapp.ui.theme.NotesViewModel
import com.example.practiceapp.ui.theme.PracticeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val notesViewModel = ViewModelProvider(this)[NotesViewModel::class.java]
        enableEdgeToEdge()
        setContent {
            PracticeAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NotesApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesApp() {
    val navController = rememberNavController()
    val viewModel: NotesViewModel = viewModel()
    var showDialog by remember { mutableStateOf(false) }
    var currentNote by remember { mutableStateOf<UserModel?>(null) }

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onSearchNotes = { navController.navigate("search") },
                onAddNotes = { navController.navigate("add") },
                onSeeInfo = { navController.navigate("info") },
                onEditNote = { note ->
                    currentNote = note
                    navController.navigate("edit")
                },
                viewModel = viewModel
            )
        }
        composable("search") {
            SearchScreen(
                onNavigateBack = { navController.popBackStack() },
                viewModel = viewModel
            )
        }
        composable("add") {
            NotesEditScreen(
                onNavigateBack = { navController.popBackStack() },
                viewModel = viewModel
            )
        }
        composable("edit") {
            NotesEditScreen(
                onNavigateBack = { navController.popBackStack() },
                viewModel = viewModel,
                noteToEdit = currentNote
            )
        }
        composable("info") {
            InfoScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}

