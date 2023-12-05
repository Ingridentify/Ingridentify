package com.ingridentify.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ingridentify.R
import com.ingridentify.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val navController: NavController by lazy { findNavController(R.id.nav_host_fragment_activity_main) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val bottomNav: BottomNavigationView = binding.bottomNav

        setupActionBarWithNavController(navController, AppBarConfiguration(setOf(
            R.id.navigation_home,
            R.id.navigation_add
        )))
        bottomNav.setupWithNavController(navController)
    }
}