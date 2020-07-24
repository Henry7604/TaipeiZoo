package com.henry.zoo.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.henry.zoo.R
import com.henry.zoo.databinding.FragmentPlantDetailBinding
import com.henry.zoo.viewModel.MainViewModel
import com.henry.zoo.viewModel.PlantDetailViewModel

/**
 * 植物詳細
 */
class PlantDetailFragment : Fragment() {
    private var mBinding : FragmentPlantDetailBinding? = null
    private val mViewModel: PlantDetailViewModel by lazy {
        ViewModelProvider(this).get(PlantDetailViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_plant_detail, container, false)
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

        val args: PlantDetailFragmentArgs by navArgs()
        val name = args.name

        val activityViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        activityViewModel.title.postValue(name)

        mViewModel.getPlant(name)
    }
}
