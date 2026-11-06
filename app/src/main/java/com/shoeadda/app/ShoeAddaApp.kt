package com.shoeadda.app

import android.app.Application
import android.util.Log
import com.shoeadda.app.feature.catalog.domain.usecase.RefreshCatalog
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@HiltAndroidApp
class ShoeAddaApp : Application() {

    @Inject
    lateinit var refreshCatalog: RefreshCatalog

    private val appScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        // Load the shoes into the database in the background every time the app starts.
        appScope.launch {
            try {
                val result = refreshCatalog()
                Log.i(TAG, "Catalog loaded: $result")
            } catch (cancelled: CancellationException) {
                throw cancelled
            } catch (@Suppress("TooGenericExceptionCaught") error: Exception) {
                Log.e(TAG, "Could not load the catalog", error)
            }
        }
    }

    private companion object {
        const val TAG = "ShoeAdda"
    }
}
