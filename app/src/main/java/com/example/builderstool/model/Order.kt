package com.example.builderstool.model

import com.google.gson.annotations.SerializedName

class Order {
    var id:Int?=null
    var billType:String?="order"
    @SerializedName("customer_id")
    var customerId:Int?=null
    @SerializedName("customer_name")
    var customerName:String?=null
    var products=ArrayList<PurchaseProducts>()
    var total:Int?=null
    var paid:Int?=null
    var balance:Int?=null
}