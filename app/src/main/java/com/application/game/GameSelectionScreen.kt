package com.application.game

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.application.game.navigation.Screen

private val BackgroundTop = Color(0xFF15152F)
private val BackgroundBottom = Color(0xFF29204A)
private val AccentOrange = Color(0xFFFF8A45)
private val AccentPurple = Color(0xFF8B6CFF)
private val CardBackground = Color(0xFF302B50)
private val TextPrimary = Color(0xFFF8F7FF)
private val TextSecondary = Color(0xFFB9B5D2)

@Composable
fun GameSelectionScreen(
    navController: NavController
) {
    BackHandler { }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = BackgroundTop
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            BackgroundTop,
                            BackgroundBottom
                        )
                    )
                )
                .padding(horizontal = 22.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(15.dp))
                            .background(AccentPurple),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Extension,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Column(
                        modifier = Modifier.padding(start = 12.dp)
                    ) {
                        Text(
                            text = "PUZZLE ARCADE",
                            color = TextPrimary,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.5.sp
                        )
                        Text(
                            text = "Your next challenge awaits",
                            color = TextSecondary,
                            fontSize = 12.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(38.dp))

                Text(
                    text = "Choose Your\nGame",
                    color = TextPrimary,
                    fontSize = 36.sp,
                    lineHeight = 42.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Pick a challenge and start playing.",
                    color = TextSecondary,
                    fontSize = 15.sp
                )

                Spacer(modifier = Modifier.height(28.dp))

                NumberMergeCard(
                    onPlayClick = {
                        navController.navigate(Screen.NumberMergeScreen.route)
                    }
                )

                Spacer(modifier = Modifier.height(18.dp))
                SnakeGameCard(onPlayClick = {
                    navController.navigate(Screen.SnakeGameScreen.route)
                })
                Text(
                    text = "MORE GAMES",
                    color = TextSecondary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                ComingSoonCard(
                    title = "2048 Classic",
                    subtitle = "Reach the ultimate number",
                    onClick = {}
                )

                Spacer(modifier = Modifier.height(12.dp))

                ComingSoonCard(
                    title = "Tile Match",
                    subtitle = "Match tiles and relax",
                    onClick = {}
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BottomItem(
                    title = "Home",
                    icon = Icons.Default.Extension,
                    selected = true,
                    onClick = {}
                )

                BottomItem(
                    title = "Stats",
                    icon = Icons.Default.BarChart,
                    onClick = {}
                )

                BottomItem(
                    title = "Settings",
                    icon = Icons.Default.Settings,
                    onClick = {  }
                )
            }
        }
    }
}

@Composable
private fun NumberMergeCard(
    onPlayClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardBackground
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "NUMBER MERGE",
                        color = AccentOrange,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.5.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Merge tiles.\nBeat your best.",
                        color = TextPrimary,
                        fontSize = 23.sp,
                        lineHeight = 29.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "A fun number puzzle for your brain.",
                        color = TextSecondary,
                        fontSize = 13.sp
                    )
                }

                NumberTilePreview()
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = onPlayClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AccentOrange,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "PLAY NOW",
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )

                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(23.dp)
                )
            }
        }
    }
}

@Composable
private fun NumberTilePreview() {
    val tiles = listOf(
        2, 4,
        8, 16
    )

    val tileColors = listOf(
        Color(0xFFFFD166),
        Color(0xFFFFA45B),
        Color(0xFF9C83FF),
        Color(0xFF62D6C7)
    )

    Column(
        modifier = Modifier
            .size(112.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(Color(0xFF211D3A))
            .padding(6.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        tiles.chunked(2).forEachIndexed { rowIndex, rowTiles ->
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                rowTiles.forEachIndexed { columnIndex, value ->
                    val index = rowIndex * 2 + columnIndex

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                            .clip(RoundedCornerShape(7.dp))
                            .background(tileColors[index]),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = value.toString(),
                            color = Color(0xFF211D3A),
                            fontSize = 17.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ComingSoonCard(
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardBackground.copy(alpha = 0.75f)
        )
    ) {
        Row(
            modifier = Modifier.padding(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(45.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF45405F)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                    tint = TextSecondary
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp)
            ) {
                Text(
                    text = title,
                    color = TextPrimary,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp
                )
                Text(
                    text = subtitle,
                    color = TextSecondary,
                    fontSize = 12.sp
                )
            }

            Text(
                text = "SOON",
                color = AccentPurple,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun BottomItem(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    selected: Boolean = false,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 18.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = if (selected) AccentOrange else TextSecondary
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = title,
            color = if (selected) AccentOrange else TextSecondary,
            fontSize = 11.sp
        )
    }
}



@Composable
private fun SnakeGameCard(
    onPlayClick: () -> Unit
) {
    val snakeGreen = Color(0xFF4ADE80)
    val cardGreen = Color(0xFF183D35)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardGreen
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Snake illustration
                Box(
                    modifier = Modifier
                        .size(104.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(Color(0xFF245448)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            SnakeTile(Color(0xFF86EFAC))
                            SnakeTile(Color(0xFF4ADE80))
                            SnakeTile(Color(0xFF4ADE80))
                        }

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            SnakeTile(Color(0xFF4ADE80))
                            SnakeTile(Color(0xFF4ADE80))
                            SnakeTile(Color(0xFF245448))
                        }

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            SnakeTile(Color(0xFF245448))
                            SnakeTile(Color(0xFF4ADE80))
                            SnakeTile(Color(0xFF245448))
                        }
                    }
                }

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        text = "ARCADE CLASSIC",
                        color = snakeGreen,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp
                    )

                    Text(
                        text = "Snake",
                        color = Color.White,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Eat. Grow. Survive.",
                        color = Color(0xFFC4E8D9),
                        fontSize = 13.sp
                    )

                    Text(
                        text = "Swipe to control your snake.",
                        color = Color(0xFFC4E8D9),
                        fontSize = 11.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Button(
                onClick = onPlayClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = snakeGreen,
                    contentColor = Color(0xFF111827)
                )
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = null
                )

                Text(
                    text = "PLAY SNAKE",
                    modifier = Modifier.padding(start = 8.dp),
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }
        }
    }
}

@Composable
private fun SnakeTile(color: Color) {
    Box(
        modifier = Modifier
            .size(21.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(color)
    )
}
