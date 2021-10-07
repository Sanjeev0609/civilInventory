package com.example.builderstool.network.request

import com.google.gson.annotations.SerializedName

class EditOrderProducts {
    @SerializedName("product_id")
    var productId:Int?=null

    @SerializedName("in_use_quantity")
    var inUseQuantity:Int?=null

    @SerializedName("excess_quantity")
    var excessQuantity:Int?=null
}