package com.pluang.stockapp.data.model

import com.google.gson.annotations.SerializedName

data class DataResponse(

    @field:SerializedName("data")
    val data: List<StockData?>? = null,

    @field:SerializedName("success")
    val success: Boolean? = null



)

 class StockData(

    @field:SerializedName("volume")
    val volume: Int? = null,

    @field:SerializedName("date")
    val date: String? = null,

    @field:SerializedName("high")
    val high: Double? = null,

    @field:SerializedName("low")
    val low: Double? = null,

    @field:SerializedName("price")
    val price: Double? = null,

    @field:SerializedName("change")
    val change: Double? = null,

    @field:SerializedName("close")
    val close: Double? = null,

    @field:SerializedName("sid")
    val sid: String? = null
)
