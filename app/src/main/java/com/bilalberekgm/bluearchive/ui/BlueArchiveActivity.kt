package com.bilalberekgm.bluearchive.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bilalberekgm.bluearchive.R
import com.bilalberekgm.bluearchive.databinding.ActivityMainBinding
import com.bilalberekgm.bluearchive.viewmodel.BlueArchiveViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BlueArchiveActivity : AppCompatActivity() {
    lateinit var bottomNav: BottomNavigationView
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setUpBottomNavBar()
    }

    private fun setUpBottomNavBar(){
        bottomNav = binding.bottomNav
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.findNavController()
        bottomNav.setupWithNavController(navController)
    }
}