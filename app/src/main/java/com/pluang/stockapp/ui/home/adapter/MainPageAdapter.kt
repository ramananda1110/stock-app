package com.pluang.stockapp.ui.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.pluang.stockapp.ui.home.view.HomeFragment
import com.pluang.stockapp.ui.home.view.WishListFragment


class MainPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                HomeFragment();
            }

            1 -> {
                WishListFragment()
                //ScannedHistoryFragment.newInstance(ScannedHistoryFragment.ResultListType.ALL_RESULT)
            }

            2 -> {
                HomeFragment()
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
