package com.example.builderstool.network.response

import com.google.gson.annotations.SerializedName

class PurchaseBalance {
    @SerializedName("purchase_id")
    var purchaseId:Int?=null

    var balance:Int?=null

    var total:Int?=null

    @SerializedName("supplier_name")
    var supplierName:String?=null

}