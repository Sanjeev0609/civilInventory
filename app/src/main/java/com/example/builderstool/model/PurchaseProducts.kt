package com.example.builderstool.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PurchaseProducts {
    @SerializedName("product_id")
    @Expose
    var id:Int?=null
    @SerializedName("product_name")
    @Expose
    var name:String?=null
    var quantity:Int?=null
    @SerializedName("excess_quantity")
    var excessQuantity:Int?=null
    @SerializedName("in_use_quantity")
    var inUseQuantity:Int?=null

    var returnQuantity:Int?=null

    var price:String?=null
    var status:String?=null


}