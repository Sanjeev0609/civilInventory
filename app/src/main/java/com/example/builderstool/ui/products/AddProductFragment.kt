package com.example.builderstool.ui.products

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.builderstool.R
import com.example.builderstool.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_add_products.*
import kotlinx.android.synthetic.main.fragment_login.*
import java.io.File

class AddProductFragment:BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_products,container,false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        observeViewModel()
    }
    val addProductsViewModel by lazy {
        ViewModelProvider(this).get(AddProductViewModel::class.java)
    }
    fun initListeners(){
        with(addProductsViewModel.createProductRequest){
            tie_product_name.addTextChangedListener {
                name=it.toString()
                til_product_name.isErrorEnabled=false
            }
            tie_product_Price.addTextChangedListener {
                price=it.toString().toInt()
                til_product_name.isErrorEnabled=false
            }
            tie_product_Stock.addTextChangedListener {
                stock=it.toString().toInt()
                til_product_Stock.isErrorEnabled=false
            }
            tie_product_description.addTextChangedListener {
                description=it.toString()
                til_product_description.isErrorEnabled=false
            }
            tie_product_Unit.addTextChangedListener {
                unit=it.toString()
                til_product_name.isErrorEnabled=false
            }
            bt_addProduct.setOnClickListener {
                addProductsViewModel.createProduct()
            }

            iv_image.setOnClickListener{
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                intent.type = "image/*"
                intent.putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/*"))
                startActivityForResult(intent, 100)
            }






        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            100 ->{
                if(resultCode==Activity.RESULT_OK && data != null && data.data != null){
                        var uri=data!!.data.toString()
                        iv_image.setImageURI(uri.toUri())
                    addProductsViewModel.createProductRequest!!.image=uri

                        Log.d("image", uri.toString())
                }
            }
        }
    }

    fun observeViewModel(){
        with(addProductsViewModel){
            isProgressShowing.observe(viewLifecycleOwner, Observer {
                updateProgress(it)
            })
            apiResponseMessage.observe(viewLifecycleOwner, Observer {
                Toast.makeText(currentContext,it,Toast.LENGTH_LONG).show()
            })
            nameError.observe(viewLifecycleOwner, Observer {

                if (TextUtils.isEmpty(it)) {
                    til_product_name.setErrorEnabled(false);
                    return@Observer;
                }
                til_product_name.setErrorEnabled(true);
                til_product_name.setError(it);
            })
            unitError.observe(viewLifecycleOwner, Observer {

                if (TextUtils.isEmpty(it)) {
                    til_product_Unit.setErrorEnabled(false);
                    return@Observer;
                }
                til_product_Unit.setErrorEnabled(true);
                til_product_Unit.setError(it);
            })
            priceError.observe(viewLifecycleOwner, Observer {

                if (TextUtils.isEmpty(it)) {
                    til_product_Price.setErrorEnabled(false);
                    return@Observer;
                }
                til_product_Price.setErrorEnabled(true);
                til_product_Price.setError(it);
            })
            stockError.observe(viewLifecycleOwner, Observer {

                if (TextUtils.isEmpty(it)) {
                    til_product_Stock.setErrorEnabled(false);
                    return@Observer;
                }
                til_product_Stock.setErrorEnabled(true);
                til_product_Stock.setError(it);
            })
        }
    }
}