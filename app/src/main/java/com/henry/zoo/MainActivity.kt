package com.henry.zoo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.henry.zoo.databinding.ActivityMainBinding
import com.henry.zoo.utility.ResourceProvider
import com.henry.zoo.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {
    private var mBinding: ActivityMainBinding? = null
    private val mViewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mBinding?.let {
            it.viewModel = mViewModel
            it.lifecycleOwner = this
            it.ivBack.setOnClickListener { onBackPressed() }
        }

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = getColor(android.R.color.white)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        val navController = findNavController(R.id.navigation_host_fragment)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.fragment_category -> {
                    mViewModel.isShowBack.postValue(false)
                    mViewModel.title.postValue(ResourceProvider.getString(R.string.home_title))
                }
                else -> {
                    mViewModel.isShowBack.postValue(true)
                }
            }
        }

    }
}