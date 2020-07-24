package com.henry.zoo.fragment


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.henry.zoo.BR
import com.henry.zoo.R
import com.henry.zoo.database.enity.Plant
import com.henry.zoo.databinding.FragmentCategoryDetailBinding
import com.henry.zoo.adapter.AdapterInterface
import com.henry.zoo.adapter.BaseRecyclerViewAdapter
import com.henry.zoo.adapter.CategoryDetailAdapter
import com.henry.zoo.adapter.ItemListener
import com.henry.zoo.viewModel.CategoryDetailViewModel
import com.henry.zoo.viewModel.MainViewModel

/**
 * 館區詳細
 */
class CategoryDetailFragment : Fragment() {
    private var mBinding: FragmentCategoryDetailBinding? = null
    private val mViewModel: CategoryDetailViewModel by lazy {
        ViewModelProvider(this).get(CategoryDetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_category_detail, container, false)
        mBinding = DataBindingUtil.bind(rootView)!!

        mBinding?.let {
            it.viewModel = mViewModel
            it.lifecycleOwner = viewLifecycleOwner
        }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val args: CategoryDetailFragmentArgs by navArgs()
        val eNo = args.eNo
        val eName = args.eName
        val activityViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        activityViewModel.title.postValue(eName)

        mViewModel.getZooAndPlantList(eNo)

        mViewModel.plantList.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                mBinding?.run {
                    if (this.rvPlant.adapter == null) {
                        val adapter = CategoryDetailAdapter(object : ItemListener<Plant> {
                            override fun onItemClick(data: Plant) {
                                if (data.eUrl.isNotEmpty()) {
                                    val intent = Intent()
                                    intent.action = Intent.ACTION_VIEW
                                    intent.data = Uri.parse(data.eUrl)
                                    startActivity(intent)
                                } else {
                                    val navController =
                                        NavHostFragment.findNavController(this@CategoryDetailFragment)
                                    val action =
                                        CategoryDetailFragmentDirections.actionPlantDetail(data.F_Name_Ch)
                                    navController.navigate(action)
                                }
                            }
                        })
                        adapter.addList(it)
                        this.rvPlant.adapter = adapter
                    }
                }
            }
        })
    }
}
