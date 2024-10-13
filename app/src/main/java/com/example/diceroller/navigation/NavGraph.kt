package com.example.diceroller.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.diceroller.DiceWithButtonAndImage
import com.example.diceroller.navigation.dice_results.DiceResult1
import com.example.diceroller.navigation.dice_results.DiceResult2
import com.example.diceroller.navigation.dice_results.DiceResult3
import com.example.diceroller.navigation.dice_results.DiceResult4
import com.example.diceroller.navigation.dice_results.DiceResult5
import com.example.diceroller.navigation.dice_results.DiceResult6

@Composable
fun NavGraph (navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = Screens.Roll.route)
    {
        composable(route = Screens.Roll.route){
            navBackStack -> //extracting the argument
                var resultShow: Int = navBackStack.arguments?.getString("result")?.toIntOrNull()?:1

            DiceWithButtonAndImage(navController = navController, resultShow = resultShow)
        }
        composable(route = Screens.DiceResult.route+ "?result={result}&prev_result={prev_result}"){
            navBackStack -> //extracting the argument
                var resultShow: Int = navBackStack.arguments?.getString("result")?.toIntOrNull()?:1
                var prevResultShow = navBackStack.arguments?.getString("prev_result")?.toIntOrNull()?:1
            when (resultShow) {
                1 -> DiceResult1(navController = navController)
                2 -> DiceResult2(navController = navController, resultShow = resultShow)
                3 -> DiceResult3(navController = navController, resultShow = resultShow)
                4 -> DiceResult4(navController = navController)
                5 -> DiceResult5(navController = navController, resultShow = resultShow)
                else -> DiceResult6(navController = navController, resultShow = resultShow, prevResultShow = prevResultShow)
            }
        }
    }
}