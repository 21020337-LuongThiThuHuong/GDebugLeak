package com.example.gdebugleak;

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.util.concurrent.Executors

class CPULeakService : Service() {

    private val executor = Executors.newSingleThreadExecutor()

    private val cpuLeakRunnable = Runnable {
        while (true) {
            for (i in 0..1000000) {
                Math.sqrt(Math.random())
            }
            Log.d("CPU_LEAK", "Running heavy task in background")
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        executor.submit(cpuLeakRunnable)
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        executor.shutdownNow()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
