package com.example.pointofsale

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.Dialog
import androidx.room.Room
import kotlinx.coroutines.launch

@Composable
fun InsertPenDialog(onDismissRequest: () -> Unit,farm: Farm) {
    Dialog(onDismissRequest = onDismissRequest) {
        // Content of your custom dialog
        // You would typically use a Surface or Card to give it a visual structure
        // For example:

        val usernameState = rememberTextFieldState()
        val context = LocalContext.current
        var number=remember { mutableStateOf(2) }
        val coroutineScope = rememberCoroutineScope()
        var pen_name = remember { mutableStateOf("") }
        var address = remember { mutableStateOf("") }

        val db = remember {
            Room.databaseBuilder(
                context,
                MenuDatabase::class.java,
                "menu.db"
            ).build()
        }
        val mm: PenDao=db.penDao

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {

            TextField(
                value = pen_name.value,
                onValueChange = { pen_name.value = it },
                label = { Text("Enter Pen name") },
                placeholder = { Text("Pen") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()

            )



            Button(onClick = {

                coroutineScope.launch {

                    mm.upsertPen(farm.id,pen_name.value))

//                number.value=+1
                }



            }) {
                Text("Add")
            }
            Button(onClick = onDismissRequest) {
                Text("close")
            }



        }

    }
}