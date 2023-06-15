package com.example.task2kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import com.example.task2kotlin.databinding.ActivityMainBinding
import com.example.task2kotlin.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {
    lateinit var im: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //splash screen 3 seconds
        val binding=ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        im = binding.splashimage
        val updateHandler = Handler()
        val runnable = Runnable {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        updateHandler.postDelayed(runnable, Constants.splashTime.toLong())
    }
}