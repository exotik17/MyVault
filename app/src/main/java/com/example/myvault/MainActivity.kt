package com.example.myvault

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.myvault.navigation.MyVaultApp
import com.example.myvault.ui.theme.MyVaultTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyVaultTheme {
                MyVaultApp()
            }
        }
    }
}
