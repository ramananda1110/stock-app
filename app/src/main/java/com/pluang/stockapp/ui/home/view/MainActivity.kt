package com.pluang.stockapp.ui.home.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.pluang.stockapp.R
import com.pluang.stockapp.databinding.ActivityMainBinding
import com.pluang.stockapp.ui.home.adapter.MainPagerAdapter
import com.pluang.stockapp.ui.home.contact.OnUpdateListener
import com.pluang.stockapp.ui.home.viewModel.StockDataViewModel


class MainActivity : AppCompatActivity(), OnUpdateListener {

    private lateinit var binding: ActivityMainBinding
    private var doubleBackToExitPressedOnce = false
    private var viewModel: StockDataViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(StockDataViewModel::class.java)

        setContentView(binding.root)

        setViewPager()
        setBottomViewListener()
        setViewPagerListener()

    }

    private fun setViewPager() {
        binding.viewPager.adapter = MainPagerAdapter(supportFragmentManager)
        //binding.viewPager
        binding.viewPager.offscreenPageLimit = 1
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

    override fun onBackPressed() {
        if (binding.viewPager.currentItem != 0) {
            binding.viewPager.currentItem = 0
            binding.bottomNav.selectedItemId = R.id.menu_home

        } else {
            exitApp()
        }
    }

    override fun onUpdateView() {
        startActivity(intent)
       // binding.viewPager.currentItem = 1
        /*val tag = "android:switcher:" + R.id.viewPager.toString() + ":" + 1
        val f = supportFragmentManager.findFragmentByTag(tag) as WishListFragment?
        f?.displayReceivedData("test update")
        binding.viewPager.currentItem = 1*/
    }

    private fun exitApp() {
        if (doubleBackToExitPressedOnce) {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NO_HISTORY
            startActivity(intent)
            finishAffinity()
            finish()
            System.exit(0)
        }
        doubleBackToExitPressedOnce = true
        Toast.makeText(this, getString(R.string.please_click_back_again_to_exit), Toast.LENGTH_LONG)
            .show()
        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 5000)
    }


}