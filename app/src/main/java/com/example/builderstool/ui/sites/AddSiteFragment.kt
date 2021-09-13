package com.example.builderstool.ui.sites

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.builderstool.R
import com.example.builderstool.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_add_site.*

class AddSiteFragment:BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_site,container,false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        observeViewModel()
    }
    val addSiteViewModel by lazy {
        ViewModelProvider(this).get(AddSiteViewModel::class.java)
    }
    fun initListeners(){
        with(addSiteViewModel.createSiteRequest){
            tie_owner_name.addTextChangedListener {
                name=it.toString()
                til_owner_name.isErrorEnabled=false
            }
            tie_address.addTextChangedListener {
                address=it.toString()
                til_address.isErrorEnabled=false
            }
            tie_street.addTextChangedListener {
                street=it.toString()
                til_street_name.isErrorEnabled=false
            }
            tie_mobile_number.addTextChangedListener {
                mobile=it.toString()
                til_mobile_number.isErrorEnabled=false
            }
            tie_details.addTextChangedListener {
                details=it.toString()
                til_site_details.isErrorEnabled=false
            }
            bt_addSite.setOnClickListener {
                addSiteViewModel.createSite()
            }





        }
    }

    fun observeViewModel(){
        with(addSiteViewModel){
            isProgressShowing.observe(viewLifecycleOwner, Observer {
                updateProgress(it)
            })
            apiResponseMessage.observe(viewLifecycleOwner, Observer {
                Toast.makeText(currentContext,it, Toast.LENGTH_LONG).show()
            })
            nameError.observe(viewLifecycleOwner, Observer {

                if (TextUtils.isEmpty(it)) {
                    til_owner_name.setErrorEnabled(false);
                    return@Observer;
                }
                til_owner_name.setErrorEnabled(true);
                til_owner_name.setError(it);
            })
            addressError.observe(viewLifecycleOwner, Observer {

                if (TextUtils.isEmpty(it)) {
                    til_address.setErrorEnabled(false);
                    return@Observer;
                }
                til_address.setErrorEnabled(true);
                til_address.setError(it);
            })
            streetError.observe(viewLifecycleOwner, Observer {

                if (TextUtils.isEmpty(it)) {
                    til_street_name.setErrorEnabled(false);
                    return@Observer;
                }
                til_street_name.setErrorEnabled(true);
                til_street_name.setError(it);
            })
            mobileError.observe(viewLifecycleOwner, Observer {

                if (TextUtils.isEmpty(it)) {
                    til_mobile_number.setErrorEnabled(false);
                    return@Observer;
                }
                til_mobile_number.setErrorEnabled(true);
                til_mobile_number.setError(it);
            })
        }
    }
}