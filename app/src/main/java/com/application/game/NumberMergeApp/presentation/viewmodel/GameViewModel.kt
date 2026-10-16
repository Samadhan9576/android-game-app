package com.application.game.NumberMergeApp.presentation.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.game.NumberMergeApp.data.ScoreRepository
import com.application.game.NumberMergeApp.domain.Direction
import com.application.game.NumberMergeApp.domain.GameEngine
import com.application.game.NumberMergeApp.domain.GameState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val engine: GameEngine,
    private val repository: ScoreRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(GameState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.bestScore.collect { best ->
                _uiState.update { it.copy(bestScore = best) }
            }
        }

        startNewGame()
    }

    fun startNewGame() {
        _uiState.update { current ->
            GameState(
                board = engine.newBoard(),
                score = 0,
                bestScore = current.bestScore,
                isGameOver = false
            )
        }
    }

    fun move(direction: Direction) {
        val current = _uiState.value
        if (current.isGameOver) return

        val result = engine.move(current.board, direction)

        if (result.board == current.board) return

        val nextScore = current.score + result.scoreGained
        val nextBoard = engine.spawnTile(result.board)
        val gameOver = !engine.canMove(nextBoard)

        _uiState.update {
            it.copy(
                board = nextBoard,
                score = nextScore,
                isGameOver = gameOver
            )
        }

        viewModelScope.launch {
            repository.saveBestScore(nextScore)
        }
    }
}