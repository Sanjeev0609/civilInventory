package com.example.builderstool.ui.register

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.builderstool.MainActivity
import com.example.builderstool.R
import com.example.builderstool.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment: BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
    }

    val registerViewModel by lazy {
        ViewModelProvider(this).get(RegisterViewModel::class.java)
    }

    fun initListeners(){
        with(registerViewModel.registerRequest){
            tie_name.addTextChangedListener {
                name=it.toString()
                til_name.isErrorEnabled=false
            }
            tie_email.addTextChangedListener {
                email=it.toString()
                til_email.isErrorEnabled=false
            }
            tie_mobile.addTextChangedListener {
                mobile=it.toString()
                til_name.isErrorEnabled=false
            }
            tie_password.addTextChangedListener {
                name=it.toString()
                til_name.isErrorEnabled=false
            }
        }
    }

    fun observeViewModel(){
        with(registerViewModel){
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
            nameError.observe(viewLifecycleOwner, Observer {

                if (TextUtils.isEmpty(it)) {
                    til_name.setErrorEnabled(false);
                    return@Observer;
                }
                til_name.setErrorEnabled(true);
                til_name.setError(it);
            })
            emailError.observe(viewLifecycleOwner, Observer {

                if (TextUtils.isEmpty(it)) {
                    til_email.setErrorEnabled(false);
                    return@Observer;
                }
                til_email.setErrorEnabled(true);
                til_email.setError(it);
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
                startActivity(Intent(requireActivity(), MainActivity::class.java))
            })
        }
    }

}


