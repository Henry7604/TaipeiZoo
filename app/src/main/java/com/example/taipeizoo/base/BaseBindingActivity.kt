package com.example.taipeizoo.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewbinding.ViewBinding
import com.example.taipeizoo.fragment.ProgressDialogFragment
import java.lang.reflect.ParameterizedType

abstract class BaseBindingActivity<VDB: ViewBinding>: AppCompatActivity() {
    lateinit var binding: VDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val method = getViewBindingClass().getMethod("inflate", LayoutInflater::class.java)
        binding = method.invoke(null, layoutInflater) as VDB
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
            insets
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun getViewBindingClass(): Class<VDB> {
        return (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VDB>
    }

    protected fun showProgress() {
        ProgressDialogFragment.show(supportFragmentManager)
    }

    protected fun hideProgress() {
        ProgressDialogFragment.dismiss(supportFragmentManager)
    }
}