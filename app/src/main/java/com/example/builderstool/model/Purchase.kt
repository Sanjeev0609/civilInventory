package com.example.builderstool.model

import com.google.gson.annotations.SerializedName

class Purchase {
    @SerializedName("supplier_id")
    var supplierId:Int?=null
    var supplier:Supplier=Supplier()
    var products=ArrayList<PurchaseProducts>()
    var total:Int?=null
    var paid:Int?=null
    var balance:Int?=null

}