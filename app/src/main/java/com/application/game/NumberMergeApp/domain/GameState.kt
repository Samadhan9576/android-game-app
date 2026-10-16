package com.application.game.NumberMergeApp.domain

data class GameState(
    val board: List<List<Int>> = List(4) { List(4) { 0 } },
    val score: Int = 0,
    val bestScore: Int = 0,
    val isGameOver: Boolean = false
)