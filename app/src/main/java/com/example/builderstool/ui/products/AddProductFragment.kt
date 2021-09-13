package com.example.builderstool.ui.products

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.builderstool.R
import com.example.builderstool.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_add_products.*
import kotlinx.android.synthetic.main.fragment_login.*

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