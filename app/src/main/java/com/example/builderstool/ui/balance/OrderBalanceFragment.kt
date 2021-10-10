package com.example.builderstool.ui.balance


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.builderstool.R
import com.example.builderstool.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_purchase_balances.*

class OrderBalanceFragment:BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_purchase_balances,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        observeViewModel()
    }
    val orderBalancesViewModel by lazy {
        ViewModelProvider(this).get(OrderBalanceViewModel::class.java)
    }
    fun init(){
        orderBalancesViewModel.getBalances()
    }
    fun observeViewModel(){
        with(orderBalancesViewModel ){
            isProgressShowing.observe(viewLifecycleOwner, Observer {
                updateProgress(it)
            })
            apiResponseMessage.observe(viewLifecycleOwner, Observer {
                Toast.makeText(currentContext,it,Toast.LENGTH_LONG).show()
            })
            balances.observe(viewLifecycleOwner, Observer {
                rv_balance.adapter=OrderBalanceAdapter(currentContext,it,orderBalancesViewModel)
                rv_balance.layoutManager=LinearLayoutManager(currentContext,LinearLayoutManager.VERTICAL,false)
            })
        }
    }
}