package com.example.builderstool.network.request

import com.google.gson.annotations.SerializedName

class CompanyRegisterRequest {

    @SerializedName("id")
    var id:Int?=null

    var name:String?=null

    var email:String?=null

    var password:String?=null
}