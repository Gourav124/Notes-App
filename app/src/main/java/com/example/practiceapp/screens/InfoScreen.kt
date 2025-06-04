package com.example.practiceapp.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.practiceapp.components.NotesTopBar

@Composable
fun InfoScreen( onNavigateBack: () -> Unit){
    Scaffold (
        modifier = Modifier.padding(start = 10.dp, top = 16.dp),
        topBar = {
            NotesTopBar(
                title = "   About App",
                onBackClick = onNavigateBack
            )
        }

    ){ padding ->
        Box(
            modifier = Modifier.padding(padding).padding(16.dp)
        ){
            Text(text = "Welcome to the Notes App – your personal space to quickly write, save, and manage notes!\n" +
                    "\n" +
                    "Whether it's a to-do list, an idea, or a quick reminder, this app helps you stay organized. It's fast, easy to use, and works completely offline — so your notes are always with you, anytime, anywhere."
            )
        }
    }
}