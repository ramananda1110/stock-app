package com.pluang.stockapp.ui.home.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pluang.stockapp.data.model.DataResponse
import com.pluang.stockapp.ui.home.repository.StockDataRepository

class StockDataViewModel(application: Application) : AndroidViewModel(application) {
    var repository: StockDataRepository = StockDataRepository()

    val updateStatus: LiveData<Boolean>
        get() = repository.updateStatus

    val stockList: MutableLiveData<DataResponse>
        get() = repository.stocksData;
}