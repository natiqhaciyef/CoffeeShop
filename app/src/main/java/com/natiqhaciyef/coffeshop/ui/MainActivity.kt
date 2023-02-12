package com.natiqhaciyef.coffeshop.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.natiqhaciyef.coffeshop.R
import com.natiqhaciyef.coffeshop.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> {
                    navigationFragments(R.id.homeFragment)
                    true
                }
                R.id.favouritesFragment -> {
                    navigationFragments(R.id.favouritesFragment)
                    true
                }
                R.id.cartFragment -> {
                    navigationFragments(R.id.cartFragment)
                    true
                }
                R.id.notificationFragment -> {
                    navigationFragments(R.id.notificationFragment)
                    true
                }
                else -> {
                    false
                }
            }
        }

    }

    private fun navigationFragments(id: Int) {
        binding.fragmentContainerView.findNavController().navigate(id)
    }
}