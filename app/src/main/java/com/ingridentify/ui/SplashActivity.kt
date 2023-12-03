package com.ingridentify.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ingridentify.databinding.ActivitySplashBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySplashBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        CoroutineScope(Dispatchers.Main).launch {
            delay(SPLASH_DURATION_MILLIS.toLong())
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }
    }

    companion object {
        private const val SPLASH_DURATION_MILLIS = 1000
    }
}