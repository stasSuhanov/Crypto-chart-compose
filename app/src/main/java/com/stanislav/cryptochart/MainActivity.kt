package com.stanislav.cryptochart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.stanislav.cryptochart.ui.screens.details.DetailsScreen
import com.stanislav.cryptochart.ui.screens.home.HomeScreen
import com.stanislav.cryptochart.ui.theme.CryptoChartTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CryptoChartTheme {
                val navController = rememberNavController()
                Surface {
                    NavHost(navController, "home") {
                        composable("home") { HomeScreen(navController) }
                        composable("coinDetails/{$ARG_KEY_COIN_SYMBOL}") { DetailsScreen() }
                    }
                }
            }
        }
    }
}

const val ARG_KEY_COIN_SYMBOL = "symbol"