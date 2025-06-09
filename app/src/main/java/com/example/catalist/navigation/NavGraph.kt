package com.example.catalist.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.catalist.model.Breed
import com.example.catalist.ui.BreedListScreen
import com.example.catalist.ui.BreedDetailsScreen
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import androidx.navigation.NavType
import androidx.navigation.navArgument

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            BreedListScreen(navController)
        }

        composable(
            "details/{breedJson}",
            arguments = listOf(navArgument("breedJson") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val encoded = backStackEntry.arguments?.getString("breedJson")
            val decoded = java.net.URLDecoder.decode(encoded, "UTF-8")
            val breed = Json.decodeFromString<Breed>(decoded)

            BreedDetailsScreen(breed = breed, navController = navController)
        }
    }
}
