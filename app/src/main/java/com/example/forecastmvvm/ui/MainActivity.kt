package com.example.forecastmvvm.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.forecastmvvm.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private companion object {
        @JvmStatic
        val TAG = "MainActivity"
    }

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setUpNavigationComponenet()
        Log.d(TAG, resources.getString(R.string.API_KEY))
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    fun setUpNavigationComponenet() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        bottom_nav.setupWithNavController(navController)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    fun sayHello(name: String = "Unknown") = Log.d(TAG, "Hello $name");
}
