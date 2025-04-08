package com.example.taipeizoo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.paging.filter
import com.example.taipeizoo.DialogUtility
import com.example.taipeizoo.R
import com.example.taipeizoo.databinding.FragmentPlantBinding
import com.example.taipeizoo.fragment.adapter.PlantAdapter
import com.example.taipeizoo.viewModel.PlantViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.IOException

@AndroidEntryPoint
class PlantFragment : BaseFragment<FragmentPlantBinding>() {
    companion object {
        fun newInstance(name: String): PlantFragment {
            val fragment = PlantFragment()
            val args = Bundle()
            args.putString("name", name)
            fragment.arguments = args
            return fragment
        }
    }

    private var mName = ""
    private val mViewModel: PlantViewModel by viewModels()
    private val mAdapter = PlantAdapter(this)

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPlantBinding {
        return FragmentPlantBinding.inflate(inflater, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mName = arguments?.getString("name") ?: ""
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvPlant.adapter = mAdapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.allPlantsPagingFlow.collectLatest { pagingData ->
                    mAdapter.submitData(pagingData.filter { it.location.contains(mName) })
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mAdapter.loadStateFlow.collectLatest { loadState ->
                    when (loadState.refresh) {
                        is LoadState.Loading -> showProgress()
                        is LoadState.NotLoading -> {
                            hideProgress()
                            if (mAdapter.itemCount == 0) {
                                binding.rvPlant.visibility = View.GONE
                                binding.tvEmpty.visibility = View.VISIBLE
                                binding.tvEmpty.text = getString(R.string.no_plant_data).format(mName)
                            } else {
                                binding.rvPlant.visibility = View.VISIBLE
                                binding.tvEmpty.visibility = View.GONE
                            }
                        }
                        else -> hideProgress()
                    }
                    val errorState = when {
                        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                        else -> null
                    }
                    errorState?.let { state ->
                        when (val throwable = state.error) {
                            is IOException -> {
                                DialogUtility.defaultNetworkError(requireActivity()) {
                                    mAdapter.retry()
                                }
                            }
                            else -> {
                                DialogUtility.defaultApiError(requireActivity(), throwable.message) {
                                    mAdapter.retry()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        binding.rvPlant.adapter = null
        super.onDestroyView()
    }
}
