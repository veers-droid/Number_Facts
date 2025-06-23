package com.example.numberfacts.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.numberfacts.ui.first_fragment.FirstFragment
import com.example.numberfacts.ui.second_fragment.SecondFragment


/**
 * Navigation class
 */
@Composable
fun AppNavGraph(navController: NavHostController, viewModelProvider: ViewModelProvider.Factory) {
    NavHost(
        navController = navController,
        startDestination = "first"
    ) {
        //navigate to main fragment
        composable("first") { FirstFragment(navController, viewModelProvider) }
        /**
         * navigate to second screen
         * $number - if empty get fact about random number
         * $fact - if not empty don't make request to get fact
         */
        composable(
            route = "second/{number}?fact={fact}",
            arguments = listOf(
                navArgument("number") { type = NavType.StringType },
                navArgument("fact") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                })
        ) { backStackEntry ->
            val number = backStackEntry.arguments?.getString("number") ?: ""
            val fact = backStackEntry.arguments?.getString("fact")
            SecondFragment(navController, viewModelProvider, number, fact)
        }
    }
}