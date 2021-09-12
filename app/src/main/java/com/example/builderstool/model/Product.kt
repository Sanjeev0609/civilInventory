package com.example.builderstool.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Product {
    var id:Int?=null
    var name:String?=null
    var stock:Int?=null
    var price:String?=null
    var description:String?=null
    var image:String?=null
    var unit:String?=null
    @SerializedName("created_date")
    @Expose
    var createdDate:String?=null
    @SerializedName("updated_date")
    @Expose
    var updatedDate:String?=null
}