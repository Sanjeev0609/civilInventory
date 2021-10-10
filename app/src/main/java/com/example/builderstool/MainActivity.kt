package com.example.builderstool

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.example.builderstool.common.BaseActivity
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import android.R.id.toggle
import android.content.Intent

import androidx.appcompat.app.ActionBar

import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.navigation.Navigation
import com.example.builderstool.common.SharedPreferenceManager
import com.example.builderstool.ui.login.LoginActivity


class MainActivity : BaseActivity() , NavigationView.OnNavigationItemSelectedListener{
    private lateinit var navController: NavController
    lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//         navController = navHostFragment.navController
        navController = findNavController(R.id.nav_host_fragment)
        setSupportActionBar(toolbar)


        val drawerLayout: DrawerLayout = drawer_layout
        val navView: NavigationView = nav_view

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.dashboardFragment
            ), drawer_layout
        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
        setupActionBarWithNavController(navController, drawer_layout)
        nav_view.setupWithNavController(navController)
        navView.setNavigationItemSelectedListener(this)
//        val actionBar: ActionBar? = this.supportActionBar
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true)
//            actionBar.setHomeButtonEnabled(true)
//        }
//
//        // the toggle allows for the simplest of open/close handling
//
//        // the toggle allows for the simplest of open/close handling
//        var toggle = ActionBarDrawerToggle(
//            this,
//            drawerLayout,
//            R.string.navigation_drawer_open,
//            R.string.navigation_drawer_close
//        )
//        // drawerListener must be set before syncState is called
//        // drawerListener must be set before syncState is called
//        drawerLayout.setDrawerListener(toggle)
//
//        toggle.setDrawerIndicatorEnabled(true)
//        toggle.syncState()

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.purchaseBalance->{
                navController.navigate(R.id.purchaseBalance)

            }
            R.id.orderBalance->{
            navController.navigate(R.id.orderBalance)
        }
            R.id.logout->{
                SharedPreferenceManager(this).logout()
                startActivity(Intent(this,LoginActivity::class.java))
                finish()
            }
        }
        drawer_layout.closeDrawers()
        return true
    }
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.activity_main_drawer, menu)
//        return false
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return super.onOptionsItemSelected(item)
//    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment)
        return NavigationUI.navigateUp(navController, drawer_layout)
    }
}