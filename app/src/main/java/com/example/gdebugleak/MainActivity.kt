package com.example.gdebugleak

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import leakcanary.LeakCanary

class MainActivity : AppCompatActivity() {

    companion object {
        var leakedActivity: MainActivity? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // LeakCanary configuration
        LeakCanary.config = LeakCanary.config.copy(dumpHeap = true)

        // Intentional memory leak
        leakedActivity = this

        findViewById<Button>(R.id.btn_memory_leak).setOnClickListener {
            createMemoryLeak()
        }

        findViewById<Button>(R.id.btn_cpu_leak).setOnClickListener {
            startService(Intent(this, CPULeakService::class.java))
            Toast.makeText(this, "CPU Leak Service Started", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createMemoryLeak() {
        Toast.makeText(this, "Memory leak created by holding reference to Activity", Toast.LENGTH_SHORT).show()
        // No additional memory allocation needed here, just keeping leakedActivity reference.
    }

    override fun onDestroy() {
        super.onDestroy()
        // Avoid cleaning leakedActivity to keep the reference alive
    }
}
