package com.pluang.stockapp.ui.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.pluang.stockapp.ui.home.view.HomeFragment
import com.pluang.stockapp.ui.home.view.ProfileFragment
import com.pluang.stockapp.ui.home.view.WishListFragment


class MainPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                HomeFragment();
            }

            1 -> {
                WishListFragment()
            }

            2 -> {
                ProfileFragment()
            }

            else -> {
                HomeFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 3
    }
}
