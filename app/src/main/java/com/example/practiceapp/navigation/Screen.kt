package com.example.practiceapp.navigation

sealed class Screen(val route : String) {
    object Home : Screen("home")
    object SearchNotes : Screen("search_notes")
    object AddNotes : Screen("add_notes")
    object Info : Screen("info")

}