package com.example.builderstool.common


import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import java.util.*
import java.util.concurrent.TimeUnit

class SharedPreferenceManager(context: Context) {
    private val gson: Gson = Gson()
    private val sharedPreferences: SharedPreferences
//    fun userLogin(userResponse: UserResponse?): Boolean {
//        val editor = sharedPreferences.edit()
//        Log.e("Store user", gson.toJson(userResponse))
//        editor.putString(STORE_USER_OBJECT, gson.toJson(userResponse))
//        editor.apply()
//        return true
//    }

//    fun updateUser(userResponse: UserResponse?): Boolean {
//        val editor = sharedPreferences.edit()
//        editor.putString(STORE_USER_OBJECT, gson.toJson(userResponse))
//        editor.apply()
//        return true
//    }

    val isLoggedIn: Boolean
        get() = sharedPreferences.getBoolean(TOKEN_STATE, false)
//    val user: UserResponse?
//        get() {
//            val userObject = sharedPreferences.getString(STORE_USER_OBJECT, null)
//                ?: return null
//            return gson.fromJson(userObject, UserResponse::class.java)
//        }

    fun logout(){
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.commit()
        editor.apply()
    }

    fun setTokens(accessToken: String?, refreshToken: String?) {
        sharedPreferences.edit()
            .putString(ACCESS_TOKEN, accessToken)
            .putString(REFRESH_TOKEN, refreshToken)
            .apply()
    }

    val accessToken: String?
        get() = sharedPreferences.getString(ACCESS_TOKEN, null)
    val refreshToken: String?
        get() = sharedPreferences.getString(REFRESH_TOKEN, null)

    fun setLastNormalUpdateShownTime() {
        val editor = sharedPreferences.edit()
        editor.putLong(LAST_NORMAL_UPDATE_SHOWN_TIME, Date().time)
        editor.apply()
    }

    fun shouldShowNormalUpdate(): Boolean {
        val millis = sharedPreferences.getLong(LAST_NORMAL_UPDATE_SHOWN_TIME, 0)
        if (millis == 0L) {
            return true
        }
        val date1 = Date(millis)
        val date2 = Date()
        val diffInMillisec = date2.time - date1.time
        val diffInHours = TimeUnit.MILLISECONDS.toHours(diffInMillisec)
        return diffInHours >= 1
    }

    companion object {
        private var mInstance: SharedPreferenceManager? = null
        private var context: Context? = null
        private const val SHARED_PREF_NAME = "bat_mobile_advertising_preferences"
        private const val STORE_USER_OBJECT = "user_object"
        private const val TOKEN_STATE = "token_state"
        private const val ACCESS_TOKEN = "access_token"
        private const val REFRESH_TOKEN = "refresh_token"
        private const val LAST_NORMAL_UPDATE_SHOWN_TIME = "last_normal_update_shown_time"
        @Synchronized
        fun getInstance(context: Context): SharedPreferenceManager? {
            if (mInstance == null) {
                mInstance = SharedPreferenceManager(context)
            }
            return mInstance
        }
    }

    init {
        Companion.context = context
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }
}