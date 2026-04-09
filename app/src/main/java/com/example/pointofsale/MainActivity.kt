package com.example.pointofsale

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField

import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.room.Room
import com.example.pointofsale.ui.theme.PointOfSaleTheme
import kotlinx.coroutines.launch
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
                            onBack = { navcontroller.popBackStack()},
                            onNext = { farmId ->
                                navcontroller.navigate(PenScreenRoute(farmId))
                            }
                        )
                    }
                    composable<PenScreenRoute> { backStackEntry ->
                        val args = backStackEntry.toRoute<PenScreenRoute>()

                        PenScreen(
                            farmId = args.farmId,
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

@Serializable
data class Farm_ser(val id:Int,val name: String, val address: String)
@Serializable
data class PenScreenRoute(val farmId: Farm_ser)

@Composable
fun FScreen(onNext: () -> Unit)
{
    Column (modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = onNext,   colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)){
            Text("Farm")

        }

    }

}

@Composable
fun LScreen(onBack: () -> Unit, onNext: (Farm_ser) -> Unit)
{  var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val db = remember {
        Room.databaseBuilder(
            context,
            MenuDatabase::class.java,
            "menu.db"
        ).build()
    }
    val mm: FarmDao=db.dao



     val  farmList by mm.getAllFarm().collectAsState(initial = emptyList())

    Column {
        Button(onClick = onBack) {
            Text("Back")
        }
        Button(onClick = { showDialog = true }) {
            Text("Add Farm")
        }

        if (showDialog) {
            CustomModalDialog(onDismissRequest = { showDialog = false })
        }

        Text("List of Farms")

        LazyColumn {
            itemsIndexed(farmList) { index, farm ->
                Row(
                    modifier = Modifier.fillMaxWidth().height(50.dp).clickable{
                        onNext(Farm_ser(farm.id,farm.farm_name,farm.address))
                    },
                    horizontalArrangement = Arrangement.Absolute.SpaceEvenly, // Distributes space evenly
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(farm.id.toString())
                    Text(farm.farm_name)
                    Text(farm.address)

                }

            }
        }


    }





}

@Composable
fun PenScreen(onBack: () -> Unit, farmId: Farm_ser)
{  var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val db = remember {
        Room.databaseBuilder(
            context,
            MenuDatabase::class.java,
            "menu.db"
        ).build()
    }
    val mm: PenDao=db.penDao



    val  penList by mm.getAllPens(farmId.id).collectAsState(initial = emptyList())

    Column {
        Text("Farm ID: ${farmId.name}")
        Button(onClick = onBack) {
            Text("Back")
        }
        Button(onClick = { showDialog = true }) {
            Text("Add Pen")
        }

        if (showDialog) {
            InsertPenDialog(onDismissRequest = { showDialog = false },farmId)
        }

        Text("List of Pens")

        LazyColumn {
            itemsIndexed(penList) { index, pen ->
                Row(
                    modifier = Modifier.fillMaxWidth().height(50.dp).clickable{

                    },
                    horizontalArrangement = Arrangement.Absolute.SpaceEvenly, // Distributes space evenly
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(pen.id.toString())
                    Text(pen.pen_name)


                }

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
        var farm_name = remember { mutableStateOf("") }
        var address = remember { mutableStateOf("") }

        val db = remember {
            Room.databaseBuilder(
                context,
                MenuDatabase::class.java,
                "menu.db"
            ).build()
        }
        val mm: FarmDao=db.dao

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {

            TextField(
                value = farm_name.value,
                onValueChange = { farm_name.value = it },
                label = { Text("Enter item name") },
                placeholder = { Text("Mango") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()

            )
            TextField(
                value = address.value,
                onValueChange = { address.value = it },
                label = { Text("Enter item type") },
                placeholder = { Text("Fruit") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()

            )


            Button(onClick = {

                coroutineScope.launch {

                    mm.upsertFarm(Farm(farm_name.value,address.value))

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