package com.example.builderstool.ui.suppliers

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.builderstool.R
import com.example.builderstool.common.BaseFragment
import com.example.builderstool.ui.sites.AddSiteViewModel
import kotlinx.android.synthetic.main.fragment_add_supplier.*

class AddSupplierFragment :BaseFragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_supplier,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        observeViewModel()
    }
    val addSupplierViewModel by lazy {
        ViewModelProvider(this).get(AddSupplierViewModel::class.java)
    }
    fun initListeners(){
        with(addSupplierViewModel.createSupplierRequest){
            tie_supplier_name.addTextChangedListener {
                name=it.toString()
                til_supplier_name.isErrorEnabled=false
            }
            tie_supplier_address.addTextChangedListener {
                address=it.toString()
                til_supplier_address.isErrorEnabled=false
            }
            tie_supplier_Remarks.addTextChangedListener {
                remarks=it.toString()
                til_supplier_remarks.isErrorEnabled=false
            }
            tie_supplier_mobile.addTextChangedListener {
                mobile=it.toString()
                til_supplier_mobile.isErrorEnabled=false
            }
            tie_supplier_details.addTextChangedListener {
                details=it.toString()
                til_supplier_details.isErrorEnabled=false
            }
            bt_add_supplier.setOnClickListener {
                addSupplierViewModel.createSupplier()
            }



        }
    }
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        when(requestCode){
//            100 ->{
//                if(resultCode== Activity.RESULT_OK && data != null && data.data != null){
//                    var uri=data!!.data.toString()
//                    iv_image.setImageURI(uri.toUri())
//                    addSupplierViewModel.createSiteRequest!!.image=uri
//
//                    Log.d("image", uri.toString())
//                }
//            }
//        }
//    }
    fun observeViewModel(){
        with(addSupplierViewModel){
            isProgressShowing.observe(viewLifecycleOwner, Observer {
                updateProgress(it)
            })
            apiResponseMessage.observe(viewLifecycleOwner, Observer {
                Toast.makeText(currentContext,it, Toast.LENGTH_LONG).show()
            })
            nameError.observe(viewLifecycleOwner, Observer {

                if (TextUtils.isEmpty(it)) {
                    til_supplier_name.setErrorEnabled(false);
                    return@Observer;
                }
                til_supplier_name.setErrorEnabled(true);
                til_supplier_name.setError(it);
            })
            addressError.observe(viewLifecycleOwner, Observer {

                if (TextUtils.isEmpty(it)) {
                    til_supplier_address.setErrorEnabled(false);
                    return@Observer;
                }
                til_supplier_address.setErrorEnabled(true);
                til_supplier_address.setError(it);
            })

            mobileError.observe(viewLifecycleOwner, Observer {

                if (TextUtils.isEmpty(it)) {
                    til_supplier_mobile.setErrorEnabled(false);
                    return@Observer;
                }
                til_supplier_mobile.setErrorEnabled(true);
                til_supplier_mobile.setError(it);
            })
        }
    }
}