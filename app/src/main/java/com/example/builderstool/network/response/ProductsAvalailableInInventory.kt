package com.example.builderstool.network.response

import com.google.gson.annotations.SerializedName

class ProductsAvalailableInInventory {
//    "id": 1,
//    "name": "bed",
//    "stock": 200,
//    "price": 3000,
//    "created_date": "2021-09-10T18:30:00.000Z",
//    "updated_date": "2021-09-16T18:30:00.000Z",
//    "unit": "kgs",
//    "description": "Helo",
//    "image": null,
//    "company_id": 1

    @SerializedName("id")
    var productId:Int?=null

    var name:String?=null

    var stock:Int?=null

    var price:Int?=null
    var image:String?=null

}