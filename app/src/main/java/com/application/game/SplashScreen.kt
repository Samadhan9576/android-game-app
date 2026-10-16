
package com.application.game

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.application.game.navigation.Screen
import kotlinx.coroutines.delay

private val BackgroundStart = Color(0xFF111127)
private val BackgroundEnd = Color(0xFF29204A)
private val TextPrimary = Color(0xFFF8F7FF)
private val TextSecondary = Color(0xFFB9B5D2)

@Composable
fun SplashScreen(
    navController: NavController
) {
    val transition = rememberInfiniteTransition(label = "splash")

    val scale by transition.animateFloat(
        initialValue = 0.94f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 900,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "logoScale"
    )

    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate(Screen.GameSelectionScreen.route)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        BackgroundStart,
                        BackgroundEnd
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Multi-game arcade logo
            Box(
                modifier = Modifier
                    .size(190.dp)
                    .scale(scale)
                    .background(
                        color = Color(0xFF302B50),
                        shape = RoundedCornerShape(34.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        GameTile("2", Color(0xFFFFD166))
                        GameTile("A", Color(0xFF8B6CFF))
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        GameTile("★", Color(0xFF62D6C7))
                        GameTile("🎮", Color(0xFFFF8A45))
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "PLAYVERSE",
                color = TextPrimary,
                fontSize = 34.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 3.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "A WORLD OF GAMES",
                color = TextSecondary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(42.dp))

            CircularProgressIndicator(
                modifier = Modifier.size(30.dp),
                color = Color(0xFFFF8A45),
                strokeWidth = 3.dp
            )
        }

        Text(
            text = "ONE APP. ENDLESS WAYS TO PLAY.",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .then(Modifier),
            color = TextSecondary,
            fontSize = 10.sp,
            letterSpacing = 1.2.sp
        )
    }
}

@Composable
private fun GameTile(
    text: String,
    color: Color
) {
    Box(
        modifier = Modifier
            .size(62.dp)
            .background(
                color = color,
                shape = RoundedCornerShape(15.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color(0xFF211D3A),
            fontSize = if (text == "🎮") 25.sp else 28.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}
