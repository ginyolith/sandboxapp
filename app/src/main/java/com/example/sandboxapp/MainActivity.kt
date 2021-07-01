package com.example.sandboxapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.sandboxapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val observer = object : LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        fun onBackground() {
            val request = OneTimeWorkRequestBuilder<HogeWorker>()
                .build()
            WorkManager
                .getInstance(this@MainActivity)
                .enqueue(request)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val request = OneTimeWorkRequestBuilder<HogeWorker>()
            .build()

        binding.startButton.setOnClickListener {
            WorkManager
                .getInstance(this)
                .enqueue(request)
        }

        ProcessLifecycleOwner.get().lifecycle.addObserver(observer)
    }

    override fun onDestroy() {
        ProcessLifecycleOwner.get().lifecycle.removeObserver(observer)
        super.onDestroy()
    }


}