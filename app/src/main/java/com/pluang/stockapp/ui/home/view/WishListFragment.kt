package com.pluang.stockapp.ui.home.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pluang.stockapp.R
import com.pluang.stockapp.data.model.StockData
import com.pluang.stockapp.databinding.FragmentHomeBinding
import com.pluang.stockapp.network.NetworkState.isNetworkAvailable
import com.pluang.stockapp.ui.home.adapter.StockListAdapter
import com.pluang.stockapp.ui.home.contact.OnCheckListener
import com.pluang.stockapp.ui.home.viewModel.StockDataViewModel
import java.util.*

class WishListFragment : Fragment(), OnCheckListener {
    var adapter: StockListAdapter? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var mContext: Context? = null
    private var viewModel: StockDataViewModel? = null
    private var binding: FragmentHomeBinding? = null

    @SuppressLint("FragmentLiveDataObserve")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(StockDataViewModel::class.java)
        layoutManager = LinearLayoutManager(context)
        mContext = activity
        if (isNetworkAvailable(requireActivity())) {
            setData();
        } else {
            Toast.makeText(
                requireActivity(),
                getString(R.string.internet_check_text),
                Toast.LENGTH_LONG
            ).show()
        }

        return binding!!.root
    }

    private fun setData() {
        viewModel!!.wishList.observe(requireActivity(), Observer {
            val wishList = it ?: return@Observer

            if (wishList.isNotEmpty()) {
                adapter = StockListAdapter(requireActivity(), wishList, this,true)
                binding!!.recyclerList.layoutManager = layoutManager
                binding!!.recyclerList.adapter = adapter
            }

        })

    }


    override fun onCheckListener(stockData: StockData?) {

    }
}