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
import com.example.taipeizoo.DialogUtility
import com.example.taipeizoo.databinding.FragmentAllAnimalBinding
import com.example.taipeizoo.fragment.adapter.AnimalAdapter
import com.example.taipeizoo.viewModel.AnimalViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.IOException

@AndroidEntryPoint
class AllAnimalFragment : BaseFragment<FragmentAllAnimalBinding>() {
    private val mViewModel: AnimalViewModel by viewModels()
    private val mAdapter = AnimalAdapter(this)

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAllAnimalBinding {
        return FragmentAllAnimalBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvAnimal.adapter = mAdapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.animalsPagingFlow.collectLatest { pagingData ->
                    mAdapter.submitData(pagingData)
                }

            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mAdapter.loadStateFlow.collectLatest { loadState ->
                    when (loadState.refresh) {
                        is LoadState.Loading -> showProgress()
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
        binding.rvAnimal.adapter = null
        super.onDestroyView()
    }
}
