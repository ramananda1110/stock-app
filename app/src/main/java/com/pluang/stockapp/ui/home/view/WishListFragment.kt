package com.pluang.stockapp.ui.home.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    private var datList: ArrayList<StockData>? = null
    private var mContext: Context? = null
    private var viewModel: StockDataViewModel? = null
    private var binding: FragmentHomeBinding? = null

    @SuppressLint("FragmentLiveDataObserve")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(StockDataViewModel::class.java)
        layoutManager = LinearLayoutManager(context)
        mContext = activity
        if (isNetworkAvailable(requireActivity())) {
            setData();
        }
        viewModel!!.updateStatus.observe(this, { status: Boolean -> loaderEnable(status) })
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    private fun setData() {

        //viewModel!!.stockList.observe(requireActivity(), datList )

        viewModel!!.stockList.observe(requireActivity(), Observer {

            val dataResponse = it ?: return@Observer

            if (!dataResponse.data?.isEmpty()!!) {
                adapter = StockListAdapter(requireActivity(), dataResponse.data, this)
                binding!!.recyclerList.layoutManager = layoutManager
                binding!!.recyclerList.adapter = adapter
            }


        })


    }

    private fun loaderEnable(status: Boolean) {
        if (status) {
            binding!!.progressView.visibility = View.VISIBLE
        } else {
            binding!!.progressView.visibility = View.GONE
        }
    }

    override fun onCheckListener(userData: StockData?) {}
}