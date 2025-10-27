package com.example.pointofsale

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RestrictTo.Scope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.window.Dialog
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.pointofsale.ui.theme.PointOfSaleTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            PointOfSaleTheme {
                val navcontroller= rememberNavController()
                NavHost(navcontroller, startDestination = FScreenRoute) {
                    composable<FScreenRoute> {
                        FScreen(
                            onNext = { navcontroller.navigate(LScreenRoute) }
                        )
                    }
                    composable<LScreenRoute> {
                        LScreen(
                            onBack = { navcontroller.popBackStack() }
                        )
                    }
                }

            }
        }
    }
}

@Serializable
object FScreenRoute

@Serializable
object LScreenRoute

@Composable
fun FScreen(onNext: () -> Unit)
{
    Column (modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = onNext,   colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)){
            Text("Menu")

        }
        Button(onClick = {}){
            Text("Customer")
        }
        Button(onClick = {}){
            Text("POS")
        }
        Button(onClick = {}){
            Text("Report")
        }
    }

}

@Composable
fun LScreen(onBack: () -> Unit)
{  var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val db = remember {
        Room.databaseBuilder(
            context,
            MenuDatabase::class.java,
            "menu.db"
        ).build()
    }
    val mm: MenuDao=db.dao


     val  menuList by mm.getAllMenu().collectAsState(initial = emptyList())

    Column {
        Button(onClick = { showDialog = true }) {
            Text("Show Custom Dialog")
        }

        if (showDialog) {
            CustomModalDialog(onDismissRequest = { showDialog = false })
        }

        Text("Hello from LScreen")
        Button(onClick = onBack) {
            Text("Go to FScreen")
        }
        LazyColumn {
            itemsIndexed(menuList) { index, menu ->
                Text(menu.item_name)
            }
        }


    }





}

@Composable
fun CustomModalDialog(onDismissRequest: () -> Unit) {
    Dialog(onDismissRequest = onDismissRequest) {
        // Content of your custom dialog
        // You would typically use a Surface or Card to give it a visual structure
        // For example:

        val usernameState = rememberTextFieldState()
        val context = LocalContext.current
        var number=remember { mutableStateOf(2) }
        val coroutineScope = rememberCoroutineScope()
        var item_name = remember { mutableStateOf("") }
        var item_type = remember { mutableStateOf("") }
        var cost = remember { mutableStateOf(0.0) }
        val db = remember {
            Room.databaseBuilder(
                context,
                MenuDatabase::class.java,
                "menu.db"
            ).build()
        }
        val mm: MenuDao=db.dao

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {

            TextField(
                value = item_name.value,
                onValueChange = { item_name.value = it },
                label = { Text("Enter item name") },
                placeholder = { Text("Mango") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()

            )
            TextField(
                value = item_type.value,
                onValueChange = { item_type.value = it },
                label = { Text("Enter item type") },
                placeholder = { Text("Fruit") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()

            )
            TextField(
                value = cost.value.toString(),
                onValueChange = { cost.value = it.toString().toDouble() },
                label = { Text("Cost") },
                placeholder = { Text("Fruit") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()

            )

            Button(onClick = {

                coroutineScope.launch {

                    mm.upsertMenu(Menu(item_name.value,item_type.value,cost.value))

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