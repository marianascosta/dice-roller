package com.example.diceroller.navigation.utils

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.diceroller.R
import com.example.diceroller.navigation.Screens

@Composable
fun BackButton(navController: NavController, result: Int = 1) {
    Button(
        onClick = {
            navController.navigate(
                Screens.Roll.route
                    .replace(
                        oldValue = "{result}",
                        newValue = result.toString()
                    )
            )
        },
    ) {
        Text(text = (stringResource(R.string.back)), fontSize = 24.sp)
    }

}