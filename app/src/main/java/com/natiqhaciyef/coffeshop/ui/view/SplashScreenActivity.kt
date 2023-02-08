package com.natiqhaciyef.coffeshop.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.natiqhaciyef.coffeshop.R
import com.natiqhaciyef.coffeshop.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}