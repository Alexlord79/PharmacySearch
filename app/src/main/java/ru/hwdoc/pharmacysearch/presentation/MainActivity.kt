package ru.hwdoc.pharmacysearch.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import ru.hwdoc.pharmacysearch.presentation.screen.pharmacies.PharmaciesScreen
import ru.hwdoc.pharmacysearch.presentation.ui.theme.PharmacySearchTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PharmacySearchTheme {
                PharmaciesScreen(
                    onPharmacyClick = {}
                )
            }
        }
    }
}