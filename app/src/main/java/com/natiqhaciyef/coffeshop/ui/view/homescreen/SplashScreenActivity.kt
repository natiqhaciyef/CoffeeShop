package com.natiqhaciyef.coffeshop.ui.view.homescreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.natiqhaciyef.coffeshop.R
import com.natiqhaciyef.coffeshop.databinding.ActivitySplashScreenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}