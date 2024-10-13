package com.example.diceroller.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.diceroller.DiceWithButtonAndImage

@Composable
fun NavGraph (navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = Screens.Roll.route)
    {
        composable(route = Screens.Roll.route){
            DiceWithButtonAndImage(navController = navController)
        }
        composable(route = Screens.DiceResult.route+ "?result={result}"){
            navBackStack -> //extracting the argument
                var resultShow: Int = navBackStack.arguments?.getString("result")?.toIntOrNull()?:1
                DiceResult(navController = navController, resultShow = resultShow)
        }
    }
}