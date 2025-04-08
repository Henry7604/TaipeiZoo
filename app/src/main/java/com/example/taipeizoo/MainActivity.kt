package com.example.taipeizoo

import android.os.Bundle
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.taipeizoo.base.BaseBindingActivity
import com.example.taipeizoo.databinding.ActivityMainBinding
import com.example.taipeizoo.fragment.AllAnimalFragment
import com.example.taipeizoo.fragment.AllPlantFragment
import com.example.taipeizoo.fragment.ParkIntroFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseBindingActivity<ActivityMainBinding>() {
    private var parkIntroFragment: ParkIntroFragment? = null
    private var animalFragment: AllAnimalFragment? = null
    private var plantFragment: AllPlantFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, 0)
            insets
        }
        changeFragment(R.id.nav_intro)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnItemSelectedListener { item ->
            changeFragment(item.itemId)
            true
        }
    }

    private fun changeFragment(navId: Int) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()

        if (fragmentManager.fragments.isNotEmpty()) {
            for (fragment in fragmentManager.fragments) {
                transaction.hide(fragment)
            }
        }

        when (navId) {
            R.id.nav_intro -> {
                if (parkIntroFragment == null) {
                    parkIntroFragment = ParkIntroFragment()
                    transaction.add(R.id.fragment_container, parkIntroFragment!!)
                } else {
                    transaction.show(parkIntroFragment!!)

                }
            }
            R.id.nav_animals -> {
                if (animalFragment == null) {
                    animalFragment = AllAnimalFragment()
                    transaction.add(R.id.fragment_container, animalFragment!!)
                } else {
                    transaction.show(animalFragment!!)
                }
            }
            R.id.nav_plants -> {
                if (plantFragment == null) {
                    plantFragment = AllPlantFragment()
                    transaction.add(R.id.fragment_container, plantFragment!!)
                } else {
                    transaction.show(plantFragment!!)
                }
            }
        }
        transaction.commit()
    }
}