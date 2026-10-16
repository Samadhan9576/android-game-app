package com.application.game.navigation

sealed class Screen(val route: String) {
    data object Splash : Screen("splash_screen")
    data object GameSelectionScreen : Screen("GameSelectionScreen")
    data object NumberMergeScreen : Screen("NumberMergeScreen")
    data object SnakeGameScreen : Screen("SnakeGameScreen")

    companion object{
        fun getStartDestination() = Splash.route
    }
}