package com.examle.littlelemonapp


import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun MyNavigation(navController: NavHostController,viewModel: MenuViewModel) {
    val context = LocalContext.current
    val sharedPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    val loggedIn = sharedPref.getBoolean("loggedIn", false)

    val startDestination = if (loggedIn) Destinations.Home.route else Destinations.Onboarding.route

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Destinations.Onboarding.route) { OnboardingScreen(navController, context) }
        composable(Destinations.Home.route) { HomeScreen(navController,viewModel) }
        composable(Destinations.Profile.route) { ProfileScreen(navController,context) }
    }
}