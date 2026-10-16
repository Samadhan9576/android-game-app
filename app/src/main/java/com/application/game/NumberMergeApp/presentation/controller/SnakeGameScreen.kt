package com.application.game.NumberMergeApp.presentation.controller

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlin.math.abs
import kotlin.random.Random

private const val GRID_SIZE = 20
private const val GAME_SPEED_MS = 250L

private val SnakeBackground = Color(0xFF111827)
private val BoardBackground = Color(0xFF1F2937)
private val GridColor = Color(0xFF293548)
private val SnakeGreen = Color(0xFF4ADE80)
private val SnakeHeadColor = Color(0xFF86EFAC)
private val FoodRed = Color(0xFFFB7185)
private val TextWhite = Color(0xFFF9FAFB)
private val ButtonBackground = Color(0xFF374151)

private data class Cell(
    val x: Int,
    val y: Int
)

private enum class SnakeDirection {
    UP, DOWN, LEFT, RIGHT
}

@Composable
fun SnakeGameScreen(
    navController: NavHostController
) {
    var snake by remember {
        mutableStateOf(
            listOf(
                Cell(10, 10),
                Cell(9, 10),
                Cell(8, 10)
            )
        )
    }

    var direction by remember {
        mutableStateOf(SnakeDirection.RIGHT)
    }

    var food by remember {
        mutableStateOf(Cell(14, 10))
    }

    var score by remember {
        mutableStateOf(0)
    }

    var isGameOver by remember {
        mutableStateOf(false)
    }

    var isPaused by remember {
        mutableStateOf(false)
    }

    // The game loop always reads the latest values.
    val currentSnake by rememberUpdatedState(snake)
    val currentDirection by rememberUpdatedState(direction)
    val currentFood by rememberUpdatedState(food)

    fun restartGame() {
        snake = listOf(
            Cell(10, 10),
            Cell(9, 10),
            Cell(8, 10)
        )
        direction = SnakeDirection.RIGHT
        food = Cell(14, 10)
        score = 0
        isGameOver = false
        isPaused = false
    }

    fun changeDirection(newDirection: SnakeDirection) {
        if (isGameOver) return

        val isOpposite = when (direction) {
            SnakeDirection.UP ->
                newDirection == SnakeDirection.DOWN

            SnakeDirection.DOWN ->
                newDirection == SnakeDirection.UP

            SnakeDirection.LEFT ->
                newDirection == SnakeDirection.RIGHT

            SnakeDirection.RIGHT ->
                newDirection == SnakeDirection.LEFT
        }

        if (!isOpposite && direction != newDirection) {
            direction = newDirection
        }
    }

    // One controlled game loop, using the latest snake and direction.
    LaunchedEffect(isGameOver, isPaused) {
        while (!isGameOver && !isPaused) {
            delay(GAME_SPEED_MS)

            val currentHead = currentSnake.first()
            val nextDirection = currentDirection

            val nextHead = when (nextDirection) {
                SnakeDirection.UP ->
                    Cell(currentHead.x, currentHead.y - 1)

                SnakeDirection.DOWN ->
                    Cell(currentHead.x, currentHead.y + 1)

                SnakeDirection.LEFT ->
                    Cell(currentHead.x - 1, currentHead.y)

                SnakeDirection.RIGHT ->
                    Cell(currentHead.x + 1, currentHead.y)
            }

            val eatingFood = nextHead == currentFood

            // The tail moves away on a normal step, so entering its
            // current cell is allowed unless the snake is eating food.
            val bodyToCheck = if (eatingFood) {
                currentSnake
            } else {
                currentSnake.dropLast(1)
            }

            val hitWall =
                nextHead.x !in 0 until GRID_SIZE ||
                        nextHead.y !in 0 until GRID_SIZE

            if (hitWall || nextHead in bodyToCheck) {
                isGameOver = true
                break
            }

            val updatedSnake = listOf(nextHead) +
                    if (eatingFood) {
                        currentSnake
                    } else {
                        currentSnake.dropLast(1)
                    }

            snake = updatedSnake

            if (eatingFood) {
                score += 10

                val emptyCells = buildList {
                    for (x in 0 until GRID_SIZE) {
                        for (y in 0 until GRID_SIZE) {
                            val cell = Cell(x, y)
                            if (cell !in updatedSnake) {
                                add(cell)
                            }
                        }
                    }
                }

                if (emptyCells.isEmpty()) {
                    isGameOver = true
                } else {
                    food = emptyCells[Random.nextInt(emptyCells.size)]
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .background(SnakeBackground)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "SNAKE",
                    color = SnakeGreen,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Classic Arcade",
                    color = Color.LightGray,
                    fontSize = 14.sp
                )
            }

            Button(
                onClick = {
                    navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonBackground
                )
            ) {
                Text(
                    text = "Games",
                    color = TextWhite
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        // Score card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(
                containerColor = BoardBackground
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "SCORE",
                        color = Color.LightGray,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Text(
                        text = score.toString(),
                        color = TextWhite,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "SNAKE LENGTH",
                        color = Color.LightGray,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Text(
                        text = snake.size.toString(),
                        color = SnakeGreen,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // Game board
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(12.dp))
                .background(BoardBackground)
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(isGameOver) {
                        var dragX = 0f
                        var dragY = 0f

                        detectDragGestures(
                            onDragEnd = {
                                val minSwipeDistance = 15f

                                if (
                                    abs(dragX) >= minSwipeDistance ||
                                    abs(dragY) >= minSwipeDistance
                                ) {
                                    if (abs(dragX) > abs(dragY)) {
                                        changeDirection(
                                            if (dragX > 0f) {
                                                SnakeDirection.RIGHT
                                            } else {
                                                SnakeDirection.LEFT
                                            }
                                        )
                                    } else {
                                        changeDirection(
                                            if (dragY > 0f) {
                                                SnakeDirection.DOWN
                                            } else {
                                                SnakeDirection.UP
                                            }
                                        )
                                    }
                                }

                                dragX = 0f
                                dragY = 0f
                            },
                            onDragCancel = {
                                dragX = 0f
                                dragY = 0f
                            }
                        ) { change, dragAmount ->
                            change.consume()
                            dragX += dragAmount.x
                            dragY += dragAmount.y
                        }
                    }
            ) {
                val cellSize = size.width / GRID_SIZE

                // Grid
                for (i in 0..GRID_SIZE) {
                    val position = i * cellSize

                    drawLine(
                        color = GridColor,
                        start = Offset(position, 0f),
                        end = Offset(position, size.height),
                        strokeWidth = 1f
                    )

                    drawLine(
                        color = GridColor,
                        start = Offset(0f, position),
                        end = Offset(size.width, position),
                        strokeWidth = 1f
                    )
                }

                // Food
                drawCircle(
                    color = FoodRed,
                    radius = cellSize * 0.36f,
                    center = Offset(
                        x = (food.x + 0.5f) * cellSize,
                        y = (food.y + 0.5f) * cellSize
                    )
                )

                // Snake
                snake.forEachIndexed { index, cell ->
                    val inset = cellSize * 0.08f

                    drawRoundRect(
                        color = if (index == 0) {
                            SnakeHeadColor
                        } else {
                            SnakeGreen
                        },
                        topLeft = Offset(
                            x = cell.x * cellSize + inset,
                            y = cell.y * cellSize + inset
                        ),
                        size = Size(
                            width = cellSize - inset * 2,
                            height = cellSize - inset * 2
                        ),
                        cornerRadius = CornerRadius(
                            cellSize * 0.2f
                        )
                    )
                }
            }

            // Game-over / pause overlay
            if (isGameOver || isPaused) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xCC111827))
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = if (isGameOver) "GAME OVER" else "PAUSED",
                        color = if (isGameOver) FoodRed else SnakeGreen,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = if (isGameOver) {
                            "Final score: $score"
                        } else {
                            "Take a break"
                        },
                        color = TextWhite,
                        fontSize = 16.sp
                    )

                    if (isGameOver) {
                        Spacer(Modifier.height(16.dp))

                        Button(
                            onClick = { restartGame() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = SnakeGreen,
                                contentColor = SnakeBackground
                            )
                        ) {
                            Text("Play Again")
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        Text(
            text = "Swipe on the board or use the arrows",
            color = Color.LightGray,
            fontSize = 13.sp
        )

        Spacer(Modifier.height(8.dp))

        // Directional controls
        SnakeControls(
            enabled = !isGameOver && !isPaused,
            onUp = { changeDirection(SnakeDirection.UP) },
            onDown = { changeDirection(SnakeDirection.DOWN) },
            onLeft = { changeDirection(SnakeDirection.LEFT) },
            onRight = { changeDirection(SnakeDirection.RIGHT) }
        )

        Spacer(Modifier.height(12.dp))

        // Pause and restart
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = { isPaused = !isPaused },
                enabled = !isGameOver,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonBackground
                )
            ) {
                Text(
                    text = if (isPaused) "Resume" else "Pause",
                    color = TextWhite
                )
            }

            Button(
                onClick = { restartGame() },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SnakeGreen,
                    contentColor = SnakeBackground
                )
            ) {
                Text("Restart")
            }
        }

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Eat the red food. Avoid the walls and your body.",
            color = Color.LightGray,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun SnakeControls(
    enabled: Boolean,
    onUp: () -> Unit,
    onDown: () -> Unit,
    onLeft: () -> Unit,
    onRight: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        DirectionButton(
            text = "↑",
            enabled = enabled,
            onClick = onUp
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DirectionButton(
                text = "←",
                enabled = enabled,
                onClick = onLeft
            )

            DirectionButton(
                text = "↓",
                enabled = enabled,
                onClick = onDown
            )

            DirectionButton(
                text = "→",
                enabled = enabled,
                onClick = onRight
            )
        }
    }
}

@Composable
private fun DirectionButton(
    text: String,
    enabled: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier.size(56.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = ButtonBackground,
            contentColor = TextWhite,
            disabledContainerColor = ButtonBackground.copy(alpha = 0.4f),
            disabledContentColor = TextWhite.copy(alpha = 0.4f)
        ),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
    ) {
        Text(
            text = text,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}