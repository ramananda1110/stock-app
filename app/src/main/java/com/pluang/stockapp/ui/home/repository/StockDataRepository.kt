package com.pluang.stockapp.ui.home.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pluang.stockapp.data.model.DataResponse
import com.pluang.stockapp.data.model.StockData
import com.pluang.stockapp.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StockDataRepository {
    private val isUpdated = MutableLiveData<Boolean>()
    val updateStatus: LiveData<Boolean>
        get() = isUpdated
    val stocksData: MutableLiveData<DataResponse?>
        get() {
            val _stockList = MutableLiveData<DataResponse?>()
            isUpdated.setValue(true)

            ApiService.create().getStocksData()?.enqueue(object : Callback<DataResponse?> {
                override fun onResponse(
                    call: Call<DataResponse?>,
                    response: Response<DataResponse?>
                ) {
                    if (response.isSuccessful) {
                        _stockList.postValue(response.body())
                        isUpdated.setValue(false)
                    }
                }

                override fun onFailure(call: Call<DataResponse?>, t: Throwable) {
                    Log.e("StockDataRepository", t.message.toString());
                    isUpdated.setValue(false)
                }
            })
            return _stockList
        }
}