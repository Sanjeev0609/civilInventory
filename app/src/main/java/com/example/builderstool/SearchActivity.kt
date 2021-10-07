package com.example.builderstool

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.widget.SearchView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.builderstool.common.BaseActivity
import com.example.builderstool.common.BaseApplication
import com.example.builderstool.network.response.SearchResponse
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.fragment_list_sites.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class SearchActivity:BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        toolbar.title=""
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        search_view.setOnQueryTextListener(object :SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onQueryTextSubmit(p0: String?): Boolean {
                searchProduct(p0.toString())
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
               return false
            }
        })


    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//         menuInflater.inflate(R.menu.menu_search,menu)
//        var search=menu!!.findItem(R.id.appSearchBar).actionView as SearchView
//        search.requestFocusFromTouch()
//        search.isFocusable=true
//        search.setIconifiedByDefault(true);
//        search.setFocusable(true);
//        search.setIconified(false);
//        search.requestFocusFromTouch();
//
////        search.expandActionView()
//        return true
//    }
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun searchProduct(query:String){
    updateProgressBar(true)
        BaseApplication.getInstance().getApiManager().searchProduct(query,object :retrofit2.Callback<SearchResponse>{
            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                updateProgressBar(false)
                if(response.isSuccessful){
                    rv_inventory.adapter=ProductsInInventoryAdapter(this@SearchActivity,response.body()!!.inventory)
                    rv_inventory.layoutManager=LinearLayoutManager(this@SearchActivity,LinearLayoutManager.VERTICAL,false)

                    rv_nearby_sites.adapter=ProductsInNearbySitesAdapter(this@SearchActivity,response.body()!!.sites)
                    rv_nearby_sites.layoutManager=LinearLayoutManager(this@SearchActivity,LinearLayoutManager.VERTICAL,false)

                }else{
                    updateProgressBar(false)
                    Toast.makeText(this@SearchActivity,"Error",Toast.LENGTH_SHORT).show()
                }

            }

            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                Toast.makeText(this@SearchActivity,t.message.toString(),Toast.LENGTH_SHORT).show()
                updateProgressBar(false)
            }
        })
    }


//    override fun onBackPressed() {
//        super.onBackPressed()
//        startActivity(Intent(this,MainActivity::class.java))
//        finish()
//    }
}