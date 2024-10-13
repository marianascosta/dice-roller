package com.example.diceroller.navigation.dice_results

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.diceroller.businesscard.BusinessCard
import com.example.diceroller.navigation.utils.BackButton

@Composable
fun DiceResult1(
    navController: NavController,
    modifier: Modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center),
) {
    Column (
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BusinessCard()
        Spacer(modifier = Modifier.height(16.dp))
        BackButton(navController)
    }
}
