package com.example.builderstool.network.response

import com.google.gson.annotations.SerializedName

class OrderBalance {


    @SerializedName("order_id")
    var orderId:Int?=null

    var balance:Int?=null

    var total:Int?=null

    @SerializedName("site_id")
    var siteId:Int?=null

    @SerializedName("supplier_name")
    var supplierName:String?=null
}