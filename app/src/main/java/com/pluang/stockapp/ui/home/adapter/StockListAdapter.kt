package com.pluang.stockapp.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.pluang.stockapp.R
import com.pluang.stockapp.data.model.StockData
import com.pluang.stockapp.databinding.StockDataListBinding
import com.pluang.stockapp.ui.home.contact.OnCheckListener

class StockListAdapter(
    private var mContext: Context,
    stockData: List<StockData?>?,
    onCheckListener: OnCheckListener,
    isWishList: Boolean
) : RecyclerView.Adapter<StockListAdapter.StockDataHolder?>() {
    var dataArrayList: List<StockData?>?
    private var layoutInflater: LayoutInflater? = null
    var isWishList: Boolean = false;
    private var onCheckListener: OnCheckListener


    init {
        dataArrayList = stockData
        this.onCheckListener = onCheckListener
        this.isWishList = isWishList
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): StockDataHolder {
        mContext = viewGroup.getContext()
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(viewGroup.getContext())
        }
        val binding: StockDataListBinding = DataBindingUtil.inflate(
            layoutInflater!!,
            R.layout.stock_data_list,
            viewGroup,
            false
        )
        return StockDataHolder(binding)
    }


    override fun onBindViewHolder(itemView: StockDataHolder, position: Int) {
        val data: StockData? = dataArrayList!![position]
        val title: String? = data?.sid
        val price: Double? = data?.price

        itemView.binding.tvTitle.setText(title);
        itemView.binding.tvPrice.setText(price.toString() + "$");

        itemView.binding.checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                onCheckListener.onCheckListener(data)
            }
        }

        if (isWishList) {
            itemView.binding.checkbox.visibility = View.GONE
        }
    }

    class StockDataHolder(val binding: StockDataListBinding) :
        RecyclerView.ViewHolder(binding.getRoot()) {

    }

    override fun getItemCount(): Int {
        return dataArrayList!!.count();
    }


}