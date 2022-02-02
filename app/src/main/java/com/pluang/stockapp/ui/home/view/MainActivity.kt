package com.pluang.stockapp.ui.home.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.pluang.stockapp.R
import com.pluang.stockapp.databinding.ActivityMainBinding
import com.pluang.stockapp.ui.home.adapter.MainPagerAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setViewPager()
        setBottomViewListener()
        setViewPagerListener()

    }

    private fun setViewPager() {
        binding.viewPager.adapter = MainPagerAdapter(supportFragmentManager)
        //binding.viewPager
        binding.viewPager.offscreenPageLimit = 2
    }

    private fun setBottomViewListener() {
        binding.bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> {
                    binding.viewPager.currentItem = 0
                }
                R.id.menu_wishlist -> {
                    binding.viewPager.currentItem = 1

                }
                R.id.menu_profile -> {
                    binding.viewPager.currentItem = 2
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
    }


    private fun setViewPagerListener() {
        binding.viewPager.setOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        binding.bottomNav.selectedItemId = R.id.menu_home
                    }
                    1 -> {
                        binding.bottomNav.selectedItemId = R.id.menu_wishlist
                    }
                    2 -> {
                        binding.bottomNav.selectedItemId = R.id.menu_profile
                    }
                }
            }
        })
    }
}