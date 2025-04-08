package com.example.taipeizoo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.taipeizoo.base.BaseBindingActivity
import com.example.taipeizoo.databinding.ActivityParkDetailBinding
import com.example.taipeizoo.fragment.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ParkDetailActivity : BaseBindingActivity<ActivityParkDetailBinding>() {
    companion object {
        const val INTENT_IMAGE_URL = "IMAGE_URL"
        const val INTENT_NAME = "NAME"
        const val INTENT_INFO = "INFO"
        const val INTENT_CATEGORY = "CATEGORY"
        const val INTENT_URL = "URL"
    }
    private var mUrl = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Glide.with(this).load(intent.getStringExtra(INTENT_IMAGE_URL)?.replaceHttp?: "").placeholder(R.drawable.logo).into(binding.ivImg)
        val name = intent.getStringExtra(INTENT_NAME) ?: ""
        binding.toolbar.title = name
        binding.tvInfo.text = intent.getStringExtra(INTENT_INFO) ?: ""
        binding.tvCategory.text = intent.getStringExtra(INTENT_CATEGORY) ?: ""
        mUrl = intent.getStringExtra(INTENT_URL)?: ""

        val adapter = ViewPagerAdapter(this, name)
        binding.viewPager.adapter = adapter

        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.tvUrl.setOnClickListener {
            if (mUrl.isEmpty()) return@setOnClickListener
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mUrl))
            startActivity(intent)
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = if (position == 0) getString(R.string.animal_data) else getString(R.string.plant_data)
        }.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}