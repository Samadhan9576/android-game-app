package com.application.game.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.application.game.GameSelectionScreen
import com.application.game.NumberMergeApp.presentation.controller.NumberMergeScreen
import com.application.game.NumberMergeApp.presentation.controller.SnakeGameScreen
import com.application.game.SplashScreen
import com.application.game.navigation.Screen.Companion.getStartDestination

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = getStartDestination(),
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(route = Screen.GameSelectionScreen.route) {
            GameSelectionScreen(navController = navController)
        }
        composable(route = Screen.NumberMergeScreen.route) {
            NumberMergeScreen(navController = navController)
        }
        composable(route = Screen.SnakeGameScreen.route) {
            SnakeGameScreen(navController = navController)
        }
    }
}