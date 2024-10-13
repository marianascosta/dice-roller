package com.example.diceroller.navigation.dice_results

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.diceroller.R
import com.example.diceroller.navigation.Screens
import com.example.diceroller.navigation.utils.BackButton

@Composable
fun DiceResult4(
    navController: NavController,
    modifier: Modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)
) {
    Column (
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BackButton(navController)
       for (i in 1..6) {
           Button_Dice_Result_Screen(i, navController)
       }
    }
}

@Composable
fun Button_Dice_Result_Screen(result : Int, navController: NavController) {
    Button(
        onClick = {
            navController.navigate(
                Screens.DiceResult.route
                    .replace(
                        oldValue = "{result}",
                        newValue = result.toString()
                    )
            )

        },
    ) {
        Text(
            text = stringResource(R.string.scree_dice, result), fontSize = 24.sp
        )
    }
}