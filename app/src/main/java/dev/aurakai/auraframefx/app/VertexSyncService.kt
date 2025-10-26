package dev.aurakai.auraframefx.app

import android.app.Service
import android.content.Intent
import android.os.IBinder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class VertexSyncService : Service() {

    private val scope = CoroutineScope(Dispatchers.Default + Job())

    override fun onCreate() {
        super.onCreate()
        Timber.i("VertexSyncService created")
        // Start background sync initialization if needed
        scope.launch {
            // TODO: inject real sync manager via Hilt and start syncing
            Timber.d("VertexSyncService background worker started")
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Timber.d("VertexSyncService onStartCommand called")
        // Handle commands or schedule work
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("VertexSyncService destroyed")
    }
}
