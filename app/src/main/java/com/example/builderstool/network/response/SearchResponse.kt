package com.example.builderstool.network.response

import com.google.gson.annotations.SerializedName

class SearchResponse {
    @SerializedName("inventory")
    var inventory=ArrayList<ProductsAvalailableInInventory>()

    @SerializedName("nearby_sites")
    var sites=ArrayList<ProductsAvailableInSites>()
}