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
    var price:String?=null


}