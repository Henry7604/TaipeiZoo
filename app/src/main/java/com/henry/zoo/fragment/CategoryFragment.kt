package com.henry.zoo.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.google.gson.Gson
import com.henry.zoo.BR
import com.henry.zoo.R
import com.henry.zoo.database.GetZooListTask
import com.henry.zoo.database.InsertPlantTask
import com.henry.zoo.database.InsertZooTask
import com.henry.zoo.database.callback.DatabaseTaskPostExecute
import com.henry.zoo.database.enity.Plant
import com.henry.zoo.database.enity.Zoo
import com.henry.zoo.databinding.FragmentCategoryBinding
import com.henry.zoo.adapter.AdapterInterface
import com.henry.zoo.adapter.BaseRecyclerViewAdapter
import com.henry.zoo.utility.KUtils
import com.henry.zoo.viewModel.CategoryViewModel

/**
 * 館區簡介
 */
class CategoryFragment : Fragment() {
    private var mBinding : FragmentCategoryBinding? = null
    private val mViewModel: CategoryViewModel by lazy {
        ViewModelProvider(this).get(CategoryViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_category, container, false)
        mBinding = DataBindingUtil.bind(rootView)!!

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mViewModel.getZoo()

        mViewModel.list.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                mBinding?.run {
                    if (this.rvCategory.adapter == null) {
                        val adapter =
                            BaseRecyclerViewAdapter(
                                R.layout.item_category,
                                object :
                                    AdapterInterface<Zoo> {
                                    override fun bindData(binding: ViewDataBinding, data: Zoo) {
                                        binding.setVariable(BR.model, data)
                                    }

                                    override fun onItemClick(data: Zoo) {
                                        val navController =
                                            NavHostFragment.findNavController(this@CategoryFragment)
                                        val action =
                                            CategoryFragmentDirections.actionCategoryDetail(
                                                data.E_Name,
                                                data.E_no
                                            )
                                        navController.navigate(action)
                                    }
                                })
                        adapter.addList(it)
                        this.rvCategory.adapter = adapter

                    }
                }
            }
        })
    }
}
