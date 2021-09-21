package com.example.builderstool.network.response

import com.google.gson.annotations.SerializedName

class PurchaseResponse {
    var message:String?=null
    @SerializedName("purchase_id")
    var purchaseId:Int?=null
}