package com.temple.crowdmanagement

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.temple.crowdmanagement.core.navigation.MainAppContainer
import com.temple.crowdmanagement.ui.theme.TempleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TempleTheme {
                MainAppContainer()
            }
        }
    }
}
