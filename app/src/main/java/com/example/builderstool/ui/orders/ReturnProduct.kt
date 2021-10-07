package com.example.builderstool.ui.orders

import com.google.gson.annotations.SerializedName

class ReturnProduct {
    @SerializedName("product_id")
    var productId:Int?=null

    @SerializedName("quantity")
    var quantity:Int?=null

    @SerializedName("return_quantity")
    var returnQuantity:Int?=null
}