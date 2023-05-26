package com.erkindilekci.stockmarketbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.erkindilekci.stockmarketbook.presentation.ui.theme.BackgroundColor
import com.erkindilekci.stockmarketbook.presentation.ui.theme.StockMarketBookTheme
import com.erkindilekci.stockmarketbook.util.ComposeNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StockMarketBookTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = BackgroundColor
                ) {
                    ComposeNavigation()
                }
            }
        }
    }
}
