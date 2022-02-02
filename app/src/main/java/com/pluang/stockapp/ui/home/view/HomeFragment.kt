package com.pluang.stockapp.ui.home.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
            Toast.makeText(requireActivity(), "message", Toast.LENGTH_LONG).show()
        }
        viewModel!!.updateStatus.observe(this, { status: Boolean -> loaderEnable(status) })
        return binding!!.root
    }

    private fun setData() {

        Log.e("printData", "loading ")

        viewModel!!.stockList.observe(requireActivity(), Observer {
            val dataResponse = it ?: return@Observer

            Log.e("printData", "hello " + dataResponse.toString())

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

    override fun onCheckListener(stockData: StockData?) {

    }


    fun saveWishData(stockData: StockData?) {
        val db = FirebaseFirestore.getInstance()
        //val data: HashMap<String, Any> = HashMap();

        /*data["sid"] = stockData?.sid.toString()
        data["price"] = stockData?.price!!.toDouble()
        data["close"] = stockData.close!!.toDouble()
        data["change"] = stockData.change!!.toDouble()
        data["high"] = stockData.high!!.toDouble()
        data["low"] = stockData.low!!.toDouble()
        data["volume"] = stockData.volume!!.toInt()
        data["date"] = stockData.date.toString()
*/

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

        }.addOnFailureListener {

        }

    }

}