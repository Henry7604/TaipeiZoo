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

        Log.d("henryyy", "create view")

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
                        val adapter =
                            BaseRecyclerViewAdapter(
                                R.layout.item_plant,
                                object :
                                    AdapterInterface<Plant> {
                                    override fun bindData(binding: ViewDataBinding, data: Plant) {
                                        binding.setVariable(BR.model, data)
                                    }

                                    override fun onItemClick(data: Plant) {
                                        val navController =
                                            NavHostFragment.findNavController(this@CategoryDetailFragment)
                                        val action =
                                            CategoryDetailFragmentDirections.actionPlantDetail(data.F_Name_Ch)
                                        navController.navigate(action)
                                    }
                                })
                        adapter.addList(it)
                        this.rvPlant.adapter = adapter
                    }
                }
            }
        })

        mViewModel.url.observe(viewLifecycleOwner, Observer { it ->
            if (it.isNotEmpty()) {
                val openUrl = getString(R.string.open_browser)
                val spannableString = SpannableString(openUrl)
                spannableString.setSpan(object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        var intent = Intent()
                        intent.action = Intent.ACTION_VIEW
                        intent.data = Uri.parse(it)
                        startActivity(intent)
                    }
                }, 0, openUrl.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

                mBinding?.let {
                    it.tvOpenUrl.text = spannableString
                    it.tvOpenUrl.movementMethod = LinkMovementMethod.getInstance()
                }
            }
        })
    }
}
