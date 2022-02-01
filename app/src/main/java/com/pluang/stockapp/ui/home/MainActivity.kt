package com.pluang.stockapp.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pluang.stockapp.R
import com.pluang.stockapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> {
                    setContent("Home")
                    true
                }

                R.id.menu_wishlist -> {
                    setContent("Search")
                    true
                }
                R.id.menu_profile -> {
                    setContent("Profile")
                    true
                }
                else -> false
            }
        }
    }

    private fun setContent(content: String) {
        setTitle(content)
        binding.tvLabel.text = content
    }
}