package com.example.taipeizoo.fragment.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.taipeizoo.fragment.AnimalFragment
import com.example.taipeizoo.fragment.PlantFragment

class ViewPagerAdapter(activity: FragmentActivity, private val name: String) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) AnimalFragment.newInstance(name) else PlantFragment.newInstance(name)
    }
}
