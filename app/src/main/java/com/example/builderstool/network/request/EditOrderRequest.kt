package com.example.builderstool.network.request

import com.example.builderstool.ui.orders.ReturnProduct
import com.google.gson.annotations.SerializedName

class EditOrderRequest {
    var products:ArrayList<EditOrderProducts>?=ArrayList()
    @SerializedName("returning_products")
    var returningProducts=ArrayList<ReturnProduct>()
    var paid:Int?=null
    var balance:Int?=null
    var total:Int?=null

}