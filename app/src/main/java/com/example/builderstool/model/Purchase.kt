package com.example.builderstool.model

import com.google.gson.annotations.SerializedName

class Purchase {
    var id:Int?=null
    var billType:String?=null
    @SerializedName("supplier_id")
    var supplierId:Int?=null
    var supplier:Supplier=Supplier()
    @SerializedName("supplier_name")
    var supplierName:String?=null
    var products=ArrayList<PurchaseProducts>()
    var total:Int?=null
    var paid:Int?=null
    var balance:Int?=null

}