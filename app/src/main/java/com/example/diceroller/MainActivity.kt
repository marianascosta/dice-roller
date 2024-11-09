package com.example.diceroller


import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.diceroller.ui.theme.DiceRollerTheme
import androidx.compose.runtime.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.diceroller.navigation.NavGraph
import com.example.diceroller.navigation.Screens
import com.example.diceroller.sensors.ShakeDetector


class MainActivity : ComponentActivity() {

    private lateinit var shakeDetector: ShakeDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shakeDetector = ShakeDetector(this)
        setContent {
            DiceRollerTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController, shakeDetector = shakeDetector)
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        shakeDetector.unregister()
    }
}


@Composable
fun HomeScreen(
    navController: NavController,
    shakeDetector: ShakeDetector,
    modifier: Modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center),
    resultShow: Int = 1
) {
    var result by remember { mutableStateOf(resultShow) }
    var previousResult by remember { mutableStateOf(resultShow) }
    val imageResource = when(result) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }

    var showToast by remember { mutableStateOf(false) }
    // Shake event handling
    LaunchedEffect(shakeDetector) {
        shakeDetector.onShakeDetected = {
            previousResult = result
            result = rollDice()
            showToast = true
        }
    }

    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp


    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (showToast)
            Toast.makeText(
                LocalContext.current,
                "Shake detected!",
                Toast.LENGTH_SHORT
            ).show()
        Image(
            painter = painterResource(imageResource),
            contentDescription = result.toString(),
            modifier = Modifier
                .pointerInput(Unit) {
                    detectDragGestures { _, dragAmount ->

                        offsetX += dragAmount.x
                        offsetY += dragAmount.y

                        val screenWidthPx = screenWidth.toPx()
                        val screenHeightPx = screenHeight.toPx()

                        val (newOffsetX, newOffsetY) = getOffsets(screenWidthPx, screenHeightPx, offsetX, offsetY)
                        offsetX = newOffsetX
                        offsetY = newOffsetY

                    }
                }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            previousResult = result
            result = rollDice()
        }) {
            Text(stringResource(R.string.roll))
        }
        Button(
            onClick = {
                navController.navigate(
                    Screens.DiceResult.route
                        .replace("{result}", result.toString())
                        .replace("{prev_result}", previousResult.toString())
                )
            },
        ) {
            Text(text = stringResource(R.string.result_screen), fontSize = 24.sp)
        }
    }
}

fun getOffsets(screenWidthPx: Float, screenHeightPx: Float, offsetX: Float, offsetY: Float): Pair<Float, Float> {
    var newOffsetX = offsetX
    var newOffsetY = offsetY

    val image = 200f
    val floorHeightPx = 100f

    if (newOffsetY < - screenHeightPx / 2 + image * 2) {
        newOffsetY = - screenHeightPx / 2 + image * 2
    }
    // Stop the dice from falling beyond the floor height
    if (newOffsetY > floorHeightPx) {
        newOffsetY = floorHeightPx
    }

    if (newOffsetX < - screenWidthPx / 2 + image) {
        newOffsetX = - screenWidthPx / 2 + image
    }

    if (newOffsetX > screenWidthPx / 2 - image) {
        newOffsetX = screenWidthPx / 2 - image
    }
    return Pair(newOffsetX, newOffsetY)
}

fun rollDice(): Int {
    return (1..6).random()
}