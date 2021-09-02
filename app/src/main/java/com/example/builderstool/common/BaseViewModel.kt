package com.example.builderstool.common

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.builderstool.R


open class BaseViewModel(application: Application) : AndroidViewModel(application) {
    protected val currentViewModel: Context = application
    val apiResponseMessage by lazy { MutableLiveData<String>() }
    val isProgressShowing by lazy { MutableLiveData<Boolean>() }
    val isPullToRefresh by lazy { MutableLiveData<Boolean>() }
    val apiResponseInId by lazy { MutableLiveData<Int>() }

    protected fun handleApiResponse(response: String?) {
        isProgressShowing.value = false
        isPullToRefresh.value = false
        if (response != null) {
            apiResponseMessage.value = response
        } else {
            apiResponseInId.value = R.string.something_went_wrong
        }
    }
    //
    protected fun handleApiResponse(response: Int?) {
        isProgressShowing.value = false
        isPullToRefresh.value = false
        if (response != null) {
            apiResponseInId.value = response
        } else {
            apiResponseInId.value = R.string.something_went_wrong
        }
    }
}