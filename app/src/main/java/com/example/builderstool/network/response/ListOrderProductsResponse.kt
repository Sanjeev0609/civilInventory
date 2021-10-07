package com.example.builderstool.network.response

import com.example.builderstool.model.PurchaseProducts
import com.google.gson.annotations.SerializedName

class ListOrderProductsResponse {
    @SerializedName("in_use")
    var inUse:ArrayList<PurchaseProducts>?= ArrayList()
    @SerializedName("excess")
    var excess:ArrayList<PurchaseProducts>?= ArrayList()
    @SerializedName("returned")
    var returned:ArrayList<PurchaseProducts>?= ArrayList()


}