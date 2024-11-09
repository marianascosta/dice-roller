package com.example.diceroller


import android.content.Context
import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.diceroller.navigation.NavGraph
import com.example.diceroller.navigation.Screens
import com.example.diceroller.sensors.SensorActivity
import kotlin.math.roundToInt
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {

    private lateinit var sensorActivity: SensorActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize SensorActivity
        sensorActivity = SensorActivity(this)
        setContent {
            DiceRollerTheme {
                SensorDataDisplay(sensorActivity)
                val navController = rememberNavController()
                DiceWithButtonAndImage(navController = navController, sensorActivity = sensorActivity)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        sensorActivity.registerListener()
    }

    override fun onPause() {
        super.onPause()
        sensorActivity.unregisterListener()
    }
}


@Composable
fun DiceWithButtonAndImage(
    navController: NavController,
    sensorActivity: SensorActivity,
    modifier: Modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center),
    resultShow: Int = 1
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

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

    // State variables to adjust offset based on accelerometer
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    // Floor height to stop the dice from falling beyond (in pixels)
    val floorHeightPx = 100f
//    LaunchedEffect(sensorActivity) {
//        sensorActivity.onSensorDataChanged = { x, y, _ ->
//            offsetX += x * 5  // 5 is a scaling factor
//            offsetY += y * 5
//
//            // Stop the dice from falling beyond the floor height
//            if (offsetY > floorHeightPx) {
//                offsetY = floorHeightPx
//            }
//
//        }
//    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Dice image with offset based on sensor data
        Image(
            painter = painterResource(imageResource),
            contentDescription = result.toString(),
            modifier = Modifier
                .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                .wrapContentSize(Alignment.Center)
                .pointerInput(Unit) {
                    detectDragGestures { _, dragAmount ->
                        // Update offset values based on drag amount
                        offsetX += dragAmount.x
                        offsetY += dragAmount.y

                        val image = 200f

                        // Convert screen size to pixels for boundary checks
                        val screenWidthPx = screenWidth.toPx()
                        val screenHeightPx = screenHeight.toPx()

                        if (offsetY < - screenHeightPx / 2 + image * 2) {
                            offsetY = - screenHeightPx / 2 + image * 2
                        }
                        // Stop the dice from falling beyond the floor height
                        if (offsetY > floorHeightPx) {
                            offsetY = floorHeightPx
                        }

                        if (offsetX < - screenWidthPx / 2 + image) {
                            offsetX = - screenWidthPx / 2 + image
                        }

                        if (offsetX > screenWidthPx / 2 - image) {
                            offsetX = screenWidthPx / 2 - image
                        }
                    }
                }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            previousResult = result
            result = (1..6).random()
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

@Composable
fun SensorDataDisplay(sensorActivity: SensorActivity) {
    // State variables to hold accelerometer data
    var x by remember { mutableStateOf(0f) }
    var y by remember { mutableStateOf(0f) }
    var z by remember { mutableStateOf(0f) }

    // Register callback to update state whenever sensor data changes
    LaunchedEffect(sensorActivity) {
        sensorActivity.onSensorDataChanged = { xData, yData, zData ->
            x = xData
            y = yData
            z = zData
        }
    }

    // Display accelerometer data in the UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Accelerometer Data", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "X: $x", fontSize = 20.sp)
        Text(text = "Y: $y", fontSize = 20.sp)
        Text(text = "Z: $z", fontSize = 20.sp)
    }
}