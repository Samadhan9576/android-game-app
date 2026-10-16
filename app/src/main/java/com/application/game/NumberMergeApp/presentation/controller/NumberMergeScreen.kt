package com.application.game.NumberMergeApp.presentation.controller


import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.application.game.NumberMergeApp.domain.Direction
import com.application.game.NumberMergeApp.domain.GameState
import com.application.game.NumberMergeApp.presentation.viewmodel.GameViewModel
import com.application.game.navigation.Screen
import kotlin.math.abs


@Composable
fun NumberMergeScreen(
    viewModel: GameViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val state = viewModel.uiState.collectAsState()
    BackHandler {
        navController.navigate(Screen.GameSelectionScreen.route)
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFFAF8EF)
    ) {
        Scaffold { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                Text(
                    text = "Number Merge",
                    fontSize = 34.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF776E65)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ScoreCard(
                        title = "SCORE",
                        score = state.value.score,
                        modifier = Modifier.weight(1f)
                    )

                    ScoreCard(
                        title = "BEST",
                        score = state.value.bestScore,
                        modifier = Modifier.weight(1f)
                    )
                }

                Text(
                    text = "Swipe to move tiles. Equal numbers merge!",
                    color = Color(0xFF776E65),
                    fontSize = 15.sp
                )

                GameBoard(
                    state = state.value,
                    onMove = viewModel::move
                )

                if (state.value.isGameOver) {
                    Text(
                        text = "Game over!",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFB33A3A),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }

                Button(
                    onClick = viewModel::startNewGame,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("New Game")
                }
            }
        }
    }
}

@Composable
private fun ScoreCard(
    title: String,
    score: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(
                color = Color(0xFFBBADA0),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(vertical = 12.dp, horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            color = Color.White,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = score.toString(),
            color = Color.White,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun GameBoard(
    state: GameState,
    onMove: (Direction) -> Unit
) {
    val gestureModifier = Modifier.pointerInput(Unit) {
        var dragX = 0f
        var dragY = 0f

        detectDragGestures(
            onDragStart = {
                dragX = 0f
                dragY = 0f
            },
            onDrag = { change, dragAmount ->
                change.consume()
                dragX += dragAmount.x
                dragY += dragAmount.y
            },
            onDragEnd = {
                val threshold = 35f

                if (
                    abs(dragX) < threshold &&
                    abs(dragY) < threshold
                ) {
                    return@detectDragGestures
                }

                if (abs(dragX) > abs(dragY)) {
                    onMove(
                        if (dragX > 0) Direction.RIGHT else Direction.LEFT
                    )
                } else {
                    onMove(
                        if (dragY > 0) Direction.DOWN else Direction.UP
                    )
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .background(
                color = Color(0xFFBBADA0),
                shape = RoundedCornerShape(10.dp)
            )
            .then(gestureModifier)
            .padding(6.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        state.board.forEach { row ->
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                row.forEach { value ->
                    Tile(
                        value = value,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun Tile(
    value: Int,
    modifier: Modifier = Modifier
) {
    val backgroundColor = when (value) {
        0 -> Color(0xFFCDC1B4)
        2 -> Color(0xFFEEE4DA)
        4 -> Color(0xFFEDE0C8)
        8 -> Color(0xFFF2B179)
        16 -> Color(0xFFF59563)
        32 -> Color(0xFFF67C5F)
        64 -> Color(0xFFF65E3B)
        else -> Color(0xFFEDCF72)
    }

    val textColor =
        if (value <= 4) Color(0xFF776E65) else Color.White

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(6.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        if (value != 0) {
            Text(
                text = value.toString(),
                fontSize = if (value >= 1024) 19.sp else 24.sp,
                fontWeight = FontWeight.Bold,
                color = textColor,
                maxLines = 1
            )
        }
    }
}