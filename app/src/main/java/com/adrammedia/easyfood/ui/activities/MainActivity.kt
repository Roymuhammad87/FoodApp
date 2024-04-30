package com.adrammedia.easyfood.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.adrammedia.easyfood.R
import com.adrammedia.easyfood.database.room.MealDb
import com.adrammedia.easyfood.databinding.ActivityMainBinding
import com.adrammedia.easyfood.ui.fragments.CategoriesFragment
import com.adrammedia.easyfood.ui.fragments.FavoriteFragment
import com.adrammedia.easyfood.ui.fragments.HomeFragment
import com.adrammedia.easyfood.viewmodel.HomeViewModel
import com.adrammedia.easyfood.viewmodel.HomeViewModelFactory
import com.adrammedia.easyfood.viewmodel.MealDetailsViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    val homeViewModel:HomeViewModel by lazy {
        val mealDb = MealDb.getInstance(this)
        val homeViewModelFactory = HomeViewModelFactory(mealDb)
        ViewModelProvider(this, homeViewModelFactory)[HomeViewModel::class.java]
    }
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigation.setOnItemSelectedListener {menuItem->
            when(menuItem.itemId) {
                R.id.homeFragment ->{
                   showHomeFragment()
                }
                R.id.categoriesFragment ->{
                  showCategoriesFragment()
                }
                R.id.favoriteFragment ->{
                 showFavoritesFragment()
                }
            }
            true
        }

    }

    private fun showHomeFragment() {
        val homeFragment = HomeFragment()
        val fragmentTransaction= supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.fragHost.id, homeFragment)
        fragmentTransaction.commit()

    }
    private fun showCategoriesFragment() {
        val categoriesFragment = CategoriesFragment()
        val fragmentTransaction= supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.fragHost.id, categoriesFragment)
        fragmentTransaction.commit()
        fragmentTransaction.addToBackStack("")
    }
    private fun showFavoritesFragment() {
        val favoriteFragment = FavoriteFragment()
        val fragmentTransaction= supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.fragHost.id, favoriteFragment)
        fragmentTransaction.commit()
        fragmentTransaction.addToBackStack("")
    }
}


