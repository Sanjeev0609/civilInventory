package com.example.builderstool.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.builderstool.MainActivity
import com.example.builderstool.R
import com.example.builderstool.ui.register.RegisterActivity
import com.example.builderstool.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_login.*



class LoginFragment: BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        observeViewModel()

    }
    val loginViewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    fun initListeners(){
        btLogin.setOnClickListener {
           loginViewModel.adminLogin()

        }
        bt_Register.setOnClickListener {
            startActivity(Intent(requireActivity(), RegisterActivity::class.java))

        }

        with(loginViewModel.loginRequest){
            tie_mobile.addTextChangedListener{
                mobile=it.toString()
                til_mobile.isErrorEnabled=false
            }
            tie_password.addTextChangedListener {
                password=it.toString()
                til_password.isErrorEnabled=false
            }
        }
    }

    fun observeViewModel(){

        with(loginViewModel) {
            isProgressShowing.observe(viewLifecycleOwner, Observer {
                updateProgress(it ?: false)
            })
            apiResponseMessage.observe(viewLifecycleOwner, Observer {
                it?.let {
                    Toast.makeText(currentContext, it, Toast.LENGTH_SHORT).show()
                }
            })
            apiResponseInId.observe(viewLifecycleOwner, Observer {
                it?.let {
                    Toast.makeText(currentContext, it, Toast.LENGTH_SHORT).show()
                }
            })

            mobileError.observe(viewLifecycleOwner, Observer {

                if (TextUtils.isEmpty(it)) {
                    til_mobile.setErrorEnabled(false);
                    return@Observer;
                }
                til_mobile.setErrorEnabled(true);
                til_mobile.setError(it);
            })
            passwordError.observe(viewLifecycleOwner, Observer {
                if (TextUtils.isEmpty(it)) {
                    til_password.setErrorEnabled(false);
                    return@Observer;
                }
                til_password.setErrorEnabled(true);
                til_password.setError(it);
            })

            navigateToMain.observe(viewLifecycleOwner, Observer {
                startActivity(Intent(requireActivity(),MainActivity::class.java))
            })
//                navigateToDashboard.observe(this@LoginActivity, Observer {
//                    it.let {
//                        if (it) {
//                            BaseApplication.getInstance().apply { setTransferUtility(Preferences(this@LoginActivity).userData?.s3Credentials!!) }
//                            startActivity(Intent(this@LoginActivity,MainActivity::class.java))
//                            finish()
//                        }
//                    }
//                })
//                navigateToOtpVerification.observe(this@LoginActivity, Observer {
//                    it.let {
//                        if (it){
//                            var intent=Intent(this@LoginActivity,RegisterActivity::class.java)
//                            intent.putExtra("navigateTo","otpVerification")
//                            startActivity(intent)
//                            finish()
//                        }
//                    }
//                })
//                navigateToAddProfile.observe(this@LoginActivity, Observer {
//                    it.let {
//                        if (it){
//                            var intent=Intent(this@LoginActivity,RegisterActivity::class.java)
//                            intent.putExtra("navigateTo","addProfile")
//                            startActivity(intent)
//                            finish()
//                        }
//                    }
//                })
        }
    }

}