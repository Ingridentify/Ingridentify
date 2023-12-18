package com.ingridentify.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ingridentify.R
import com.ingridentify.databinding.ActivityMainBinding
import com.ingridentify.ui.ViewModelFactory
import com.ingridentify.ui.auth.AuthActivity

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val navController: NavController by lazy { findNavController(R.id.nav_host_fragment_activity_main) }
    private val viewModel by viewModels<MainViewModel> { ViewModelFactory.getInstance(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val bottomNav: BottomNavigationView = binding.bottomNav

        setupActionBarWithNavController(navController, AppBarConfiguration(setOf(
            R.id.navigation_home,
            R.id.navigation_add,
            R.id.navigation_recipe
        )))
        bottomNav.setupWithNavController(navController)

        viewModel.checkSession().observe(this) { userModel ->
            if (userModel == null) {
                startActivity(Intent(this@MainActivity, AuthActivity::class.java))
                finish()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_logout -> {
                viewModel.logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}