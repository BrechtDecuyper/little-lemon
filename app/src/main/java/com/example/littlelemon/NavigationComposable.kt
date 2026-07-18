package com.example.littlelemon

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController

@Composable
fun Navigation(navController: NavHostController) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences(Navigation.USER_SHARED, Context.MODE_PRIVATE)
    val hasUserData = sharedPreferences.contains(Navigation.USER_EMAIL)

    NavHost(
        navController = navController,
        startDestination = if (hasUserData) DestOnboarding.route else DestHome.route
    ) {
        composable(DestHome.route) {
            Home(navController)
        }
        composable(DestProfile.route) {
            Profile(navController)
        }
        composable(DestOnboarding.route) {
            Onboarding(navController)
        }
    }
}

class Navigation {
    companion object {
        const val USER_SHARED = "USER_LittleLemon"
        const val USER_FIRSTNAME = "USER_Firstname"
        const val USER_LASTNAME = "USER_Lastname"
        const val USER_EMAIL = "USER_Email"
    }
}