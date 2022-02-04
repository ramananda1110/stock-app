package com.pluang.stockapp.ui.home.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.pluang.stockapp.R
import com.pluang.stockapp.data.model.StockData
import com.pluang.stockapp.databinding.FragmentHomeBinding
import com.pluang.stockapp.network.NetworkState
import com.pluang.stockapp.ui.home.adapter.StockListAdapter
import com.pluang.stockapp.ui.home.contact.OnCheckListener
import com.pluang.stockapp.ui.home.viewModel.StockDataViewModel


class HomeFragment : Fragment(), OnCheckListener {
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

        if (NetworkState.isNetworkAvailable(requireActivity())) {
            setData();
        } else {
            Toast.makeText(
                requireActivity(),
                getString(R.string.internet_check_text),
                Toast.LENGTH_LONG
            ).show()
        }
        viewModel!!.updateStatus.observe(this, { status: Boolean ->

            binding!!.progressBar = status


        })
        return binding!!.root
    }

    private fun setData() {
        viewModel!!.stockList.observe(requireActivity(), Observer {
            val dataResponse = it ?: return@Observer

            if (!dataResponse.data?.isEmpty()!!) {
                adapter = StockListAdapter(requireActivity(), dataResponse.data, this)
                binding!!.recyclerList.layoutManager = layoutManager
                binding!!.recyclerList.adapter = adapter
            }

        })


    }

    override fun onCheckListener(stockData: StockData?) {

        saveWishData(stockData)
    }


    fun saveWishData(stockData: StockData?) {
        val db = FirebaseFirestore.getInstance()

        val collection = db.collection("stock_data")
        val data = hashMapOf(
            "sid" to stockData?.sid,
            "price" to stockData?.price,
            "close" to stockData?.close,
            "change" to stockData?.change,
            "high" to stockData?.high,
            "low" to stockData?.low,
            "volume" to stockData?.volume,
            "date" to stockData?.date
        )

        collection.document(stockData?.sid.toString()).set(data).addOnSuccessListener {
            Toast.makeText(requireActivity(), "Data added to wishlist.", Toast.LENGTH_LONG).show()
        }.addOnFailureListener {
            Toast.makeText(requireActivity(), "Failed!", Toast.LENGTH_LONG).show()
        }

    }

}