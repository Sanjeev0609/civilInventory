package com.example.builderstool.network.response

import com.google.gson.annotations.SerializedName

class ProductsAvailableInSites {
//    "product_id": 1,
//    "product_name": "bed",
//    "excess_quantity": 2,
//    "street": "1st",
//    "site_name": "Sanjeev",
//    "site_id": 2

    @SerializedName("product_id")
    var productId:Int?=null

    @SerializedName("product_name")
    var productName:String?=null

    @SerializedName("excess_quantity")
    var quantity:Int?=null

    @SerializedName("site_name")
    var siteName:String?=null

    var street:String?=null
    var price:Int=0
    var image:String?=null


}