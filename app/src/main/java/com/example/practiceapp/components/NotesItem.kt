package com.example.practiceapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practiceapp.database.UserModel

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

@Composable
fun NotesItem(
    item: UserModel,
    onDelete: () -> Unit,
    onNoteClick: () -> Unit,
    index: Int = 0
) {
    val isDarkTheme = MaterialTheme.colorScheme.background.luminance() < 0.5f
    val cardColors = if (isDarkTheme) darkThemeColors else lightThemeColors
    val cardColor = cardColors[index % cardColors.size]
    
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .clip(RoundedCornerShape(16.dp))
                .clickable(onClick = onNoteClick),
            colors = CardDefaults.cardColors(
                containerColor = cardColor
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            )
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = item.title,
                    fontSize = 25.sp,
                    modifier = Modifier.padding(16.dp),
                    color = if (isDarkTheme) Color.White else Color.Black
                )
                Text(
                    text = item.content,
                    fontSize = 22.sp,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    color = if (isDarkTheme) Color.White else Color.Black
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = onDelete) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "delete",
                            tint = MaterialTheme.colorScheme.background
                        )
                    }
                }
            }
        }
    }
}