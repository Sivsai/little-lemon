package com.examle.littlelemonapp


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.examle.littlelemonapp.ui.theme.LittleLemonappTheme
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class MainActivity : ComponentActivity() {
    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true // Good for handling API changes
            },
                contentType = ContentType.Any
            )
        }
    }
    private suspend fun fetchMenu(): List<MenuItemNetwork> {
        val url = "https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json"
        return try {
            val response = client.get(url).body<MenuNetwork>()
            response.menu
        } catch (e: Exception) {
            // Log the exception or handle it
            // ADD THIS LOG to see if there's a hidden error
            Log.e("fetchMenu", "Failed to fetch menu", e)
            emptyList() // It will return an empty list if an error occurs
        }
    }
    private lateinit var menuItems: Flow<List<MenuItemEntity>>
     private val db by   lazy { Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java, "menu-db"
    ).build()
    }
   private val viewModel: MenuViewModel by viewModels { MenuViewModelFactory(db.menuDao()) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


lifecycleScope.launch(Dispatchers.IO) {
    if (db.menuDao().isEmpty()) { // Only fetch if the DB is empty
        val menu= fetchMenu()
        Log.d("MainActivity", "1. Fetched ${menu.size} items from network.")
        val entities = menu.map {
            MenuItemEntity(it.id, it.title, it.description, it.price, it.image, it.category)
        }
        Log.d("MainActivity", "2. About to insert ${entities.size} items into the database.")

        db.menuDao().insertAll(entities)
        Log.d("MainActivity", "3. Database insertion call has completed.")

    }
    else {
        Log.d("MainActivity", "Database is not empty. Skipping network fetch.")
    }

}
        menuItems  = db.menuDao().getAllMenuItems()


        enableEdgeToEdge()
        setContent {
            LittleLemonappTheme {

                val navController = rememberNavController()

                MyNavigation(navController,viewModel)

            }

        }
    }
}

