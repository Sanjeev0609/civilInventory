package com.example.builderstool.common


import android.app.Application
import android.util.Log
import android.widget.Toast
import com.example.builderstool.network.RetrofitService


class BaseApplication: Application() {
    private lateinit var preferences:SharedPreferenceManager
    lateinit var apimanager: RetrofitService

    private lateinit var bucketName: String
    private lateinit var folderName: String
    private lateinit var buildFlavour:String

    companion object{
        private lateinit var applicationInstance: BaseApplication
        private val TAG = BaseApplication::class.java.simpleName
        fun getInstance(): BaseApplication = applicationInstance
    }

    override fun onCreate() {
        super.onCreate()
        init()
    }
    fun init(){
        applicationInstance=this
        apimanager= RetrofitService()
//        buildFlavour=BuildConfig.FLAVOR.toString()
        preferences= SharedPreferenceManager(this)

    }

    fun getApiManager():RetrofitService=apimanager
    fun getPreferences():SharedPreferenceManager=preferences



}