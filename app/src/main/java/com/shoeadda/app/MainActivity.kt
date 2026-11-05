package com.shoeadda.app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shoeadda.app.core.designsystem.ShoeAddaTheme
import com.shoeadda.app.feature.start.presentation.StartGateViewModel
import com.shoeadda.app.navigation.AppNavDisplay
import dagger.hilt.android.AndroidEntryPoint

// AppCompatActivity is needed so the per-app language also works on Android 12 and older.
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val startGate: StartGateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // The system splash stays on screen until the saved state is read, so no second splash is needed.
        splashScreen.setKeepOnScreenCondition { startGate.initialDestination.value == null }

        setContent {
            ShoeAddaTheme {
                val destination by startGate.initialDestination.collectAsStateWithLifecycle()
                destination?.let { AppNavDisplay(startDestination = it) }
            }
        }
    }
}
