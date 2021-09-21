package com.example.builderstool.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.builderstool.MainActivity
import com.example.builderstool.R
import com.example.builderstool.common.BaseApplication
import com.example.builderstool.common.SharedPreferenceManager

class SplashActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_splash)

        if(SharedPreferenceManager(BaseApplication.getInstance()).user!!.id!=null){
            Handler().postDelayed({startActivity(Intent(this,MainActivity::class.java))},5000)

        }else{
            Handler().postDelayed({startActivity(Intent(this,MainActivity::class.java))},5000)
        }
    }
}