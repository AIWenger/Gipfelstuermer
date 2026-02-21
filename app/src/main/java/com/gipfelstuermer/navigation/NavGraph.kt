package com.gipfelstuermer.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.gipfelstuermer.ui.screens.GameDetailScreen
import com.gipfelstuermer.ui.screens.HomeScreen
import com.gipfelstuermer.ui.viewmodel.GameViewModel

object Routes {
    const val HOME = "home"
    const val GAME_DETAIL = "game/{gameId}"

    fun gameDetail(gameId: Int) = "game/$gameId"
}

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: GameViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.HOME,
        modifier = modifier
    ) {
        composable(
            route = Routes.HOME,
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }) }
        ) {
            HomeScreen(
                viewModel = viewModel,
                onGameClick = { gameId ->
                    navController.navigate(Routes.gameDetail(gameId))
                }
            )
        }

        composable(
            route = Routes.GAME_DETAIL,
            arguments = listOf(
                navArgument("gameId") { type = NavType.IntType }
            ),
            enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { it }) },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }) },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) }
        ) { backStackEntry ->
            val gameId = backStackEntry.arguments?.getInt("gameId") ?: return@composable
            GameDetailScreen(
                gameId = gameId,
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
