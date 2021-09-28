package com.example.builderstool.network.response

import com.google.gson.annotations.SerializedName

class UserResponse{

    var id:Int?=null

    @SerializedName("username")
    var name:String?=null

    var email:String?=null

    var mobile:String?=null
}