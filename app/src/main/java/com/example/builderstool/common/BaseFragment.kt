package com.example.builderstool.common

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.google.android.material.bottomnavigation.BottomNavigationView

open class BaseFragment : Fragment(){
    private val TAG = BaseFragment::class.java.simpleName
    private var progressBar: ProgressBarCallBack? = null
//    private var bottomNavigation: BottomNavigationCallBack?=null
    protected lateinit var currentContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        currentContext = context
        try {
            progressBar = context as? ProgressBarCallBack?
//            bottomNavigation=context as? BottomNavigationCallBack

        } catch (exception: ClassCastException) {
            Log.e(TAG, "onAttach: ClassCastException:", exception)
        }
    }

    protected fun updateProgress(shouldShowProgress: Boolean) {
        progressBar?.updateProgressBar(shouldShowProgress)
    }

    fun showMessage(message:String){
        Toast.makeText(currentContext,message,Toast.LENGTH_SHORT).show()
    }

//    protected fun setBottomNavigation(visibility:Boolean,view:BottomNavigationView,navController: NavController){
//        bottomNavigation?.setBottomNavigation(visibility,view,navController)
//    }

}