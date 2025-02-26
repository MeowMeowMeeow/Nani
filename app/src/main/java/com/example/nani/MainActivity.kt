package com.example.nani

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.nani.screens.SignUpScreen
import com.example.nani.ui.theme.NaNiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NaNiTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier
                ) {
                    JairosoftApp()

                }
            }
        }
    }
}



