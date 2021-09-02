package com.example.builderstool.common

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.view.*
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.ui.setupWithNavController
import com.example.builderstool.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import jp.wasabeef.blurry.Blurry


open class BaseActivity :AppCompatActivity(),
    ProgressBarCallBack{
    private var isProgressShowing = false
    private var relativeLayout: RelativeLayout? = null
    private var mWindow: Window? = null
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE or WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun updateProgressBar(visibleMode: Boolean) {
        if (visibleMode) {
            if (!isProgressShowing) {
                isProgressShowing = true
                mWindow = window
                mWindow?.setFlags(
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                )
                relativeLayout = RelativeLayout(this)
                val progressBar = ProgressBar(this)
                progressBar.isIndeterminate = true
                progressBar.setProgressTintList(ColorStateList.valueOf(Color.RED))

                val paramsForProgressBar = RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
                )
                paramsForProgressBar.addRule(RelativeLayout.CENTER_IN_PARENT)
                relativeLayout?.addView(progressBar, paramsForProgressBar)
                val paramsForRelativeLayout = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
                )
                relativeLayout?.setBackgroundColor(
                    ContextCompat.getColor(this, R.color.colorProgressBar)
                )
                paramsForRelativeLayout.gravity = Gravity.CENTER
                addContentView(relativeLayout, paramsForRelativeLayout)
                Blurry.with(this)
                    .async()
                    .onto(relativeLayout)
            }
        } else {
            isProgressShowing = false
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            (relativeLayout?.parent as? ViewGroup?)?.removeView(relativeLayout)
        }
    }


}