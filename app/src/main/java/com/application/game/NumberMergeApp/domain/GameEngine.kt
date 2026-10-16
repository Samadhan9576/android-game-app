package com.application.game.NumberMergeApp.domain


import javax.inject.Inject
import kotlin.random.Random

data class MoveResult(
    val board: List<List<Int>>,
    val scoreGained: Int
)

class GameEngine @Inject constructor() {

    companion object {
        const val SIZE = 4
    }

    fun newBoard(): List<List<Int>> {
        var board = emptyBoard()
        board = spawnTile(board)
        board = spawnTile(board)
        return board
    }

    fun emptyBoard(): List<List<Int>> =
        List(SIZE) { List(SIZE) { 0 } }

    fun move(
        board: List<List<Int>>,
        direction: Direction
    ): MoveResult {
        val result = board.map { it.toMutableList() }.toMutableList()
        var scoreGained = 0

        for (index in 0 until SIZE) {
            val line = when (direction) {
                Direction.LEFT ->
                    (0 until SIZE).map { board[index][it] }

                Direction.RIGHT ->
                    (0 until SIZE).map { board[index][SIZE - 1 - it] }

                Direction.UP ->
                    (0 until SIZE).map { board[it][index] }

                Direction.DOWN ->
                    (0 until SIZE).map { board[SIZE - 1 - it][index] }
            }

            val (merged, gained) = mergeLine(line)
            scoreGained += gained

            for (position in 0 until SIZE) {
                val row: Int
                val col: Int

                when (direction) {
                    Direction.LEFT -> {
                        row = index
                        col = position
                    }
                    Direction.RIGHT -> {
                        row = index
                        col = SIZE - 1 - position
                    }
                    Direction.UP -> {
                        row = position
                        col = index
                    }
                    Direction.DOWN -> {
                        row = SIZE - 1 - position
                        col = index
                    }
                }

                result[row][col] = merged[position]
            }
        }

        return MoveResult(
            board = result.map { it.toList() },
            scoreGained = scoreGained
        )
    }

    private fun mergeLine(
        line: List<Int>
    ): Pair<List<Int>, Int> {
        val values = line.filter { it != 0 }
        val merged = mutableListOf<Int>()
        var score = 0
        var index = 0

        while (index < values.size) {
            if (
                index + 1 < values.size &&
                values[index] == values[index + 1]
            ) {
                val value = values[index] * 2
                merged.add(value)
                score += value
                index += 2
            } else {
                merged.add(values[index])
                index++
            }
        }

        while (merged.size < SIZE) {
            merged.add(0)
        }

        return merged to score
    }

    fun spawnTile(
        board: List<List<Int>>
    ): List<List<Int>> {
        val emptyCells = buildList {
            for (row in 0 until SIZE) {
                for (col in 0 until SIZE) {
                    if (board[row][col] == 0) {
                        add(row to col)
                    }
                }
            }
        }

        if (emptyCells.isEmpty()) return board

        val (row, col) = emptyCells[Random.nextInt(emptyCells.size)]
        val value = if (Random.nextInt(10) == 0) 4 else 2

        return board.mapIndexed { r, values ->
            values.mapIndexed { c, current ->
                if (r == row && c == col) value else current
            }
        }
    }

    fun canMove(board: List<List<Int>>): Boolean {
        if (board.any { row -> row.any { it == 0 } }) {
            return true
        }

        for (row in 0 until SIZE) {
            for (col in 0 until SIZE) {
                if (
                    col + 1 < SIZE &&
                    board[row][col] == board[row][col + 1]
                ) {
                    return true
                }

                if (
                    row + 1 < SIZE &&
                    board[row][col] == board[row + 1][col]
                ) {
                    return true
                }
            }
        }

        return false
    }
}