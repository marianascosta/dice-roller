package com.example.diceroller.navigation.dice_results

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.diceroller.R
import com.example.diceroller.navigation.utils.BackButton

@Composable
fun DiceResult5(
    navController: NavController,
    modifier: Modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center),
    resultShow: Int = 1
) {
    var result by remember { mutableStateOf(resultShow) }
    val imageResource = when(result) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }
    Column (
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(imageResource),
            contentDescription = result.toString()
        )
        Spacer(modifier = Modifier.height(16.dp))

        var textState by remember { mutableStateOf(result.toString()) }

        TextField(
            value = textState,
            onValueChange = { newValue ->
                textState = newValue
                val newResult = newValue.toIntOrNull()
                if (newResult != null && newResult in 1..6) {
                    result = newResult
                }
            },
            label = { Text(text = "Enter Dice Result (1-6)") },
        )
        Spacer(modifier = Modifier.height(16.dp))
        BackButton(navController, result)

    }
}
