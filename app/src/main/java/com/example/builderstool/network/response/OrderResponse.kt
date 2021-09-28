package com.example.builderstool.network.response

import com.google.gson.annotations.SerializedName

class OrderResponse {
    @SerializedName("order_id")
    var orderId:Int?=null
    var message:String?=null
}