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
import com.example.taipeizoo.databinding.FragmentAnimalBinding
import com.example.taipeizoo.fragment.adapter.AnimalAdapter
import com.example.taipeizoo.viewModel.AnimalViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.IOException

@AndroidEntryPoint
class AnimalFragment : BaseFragment<FragmentAnimalBinding>() {
    companion object {
        fun newInstance(name: String): AnimalFragment {
            val fragment = AnimalFragment()
            val args = Bundle()
            args.putString("name", name)
            fragment.arguments = args
            return fragment
        }
    }
    private var mName = ""
    private val mViewModel: AnimalViewModel by viewModels()
    private val mAdapter by lazy {
        AnimalAdapter(this)
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAnimalBinding {
        return FragmentAnimalBinding.inflate(inflater, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mName = arguments?.getString("name") ?: ""
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvAnimal.adapter = mAdapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.allAnimalsPagingFlow.collectLatest { pagingData ->
                    mAdapter.submitData(pagingData.filter { it.location.contains(mName) })
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            mAdapter.loadStateFlow.collectLatest { loadState ->
                when (loadState.refresh) {
                    is LoadState.Loading -> showProgress()
                    is LoadState.NotLoading -> {
                        hideProgress()
                        if (mAdapter.itemCount == 0) {
                            binding.rvAnimal.visibility = View.GONE
                            binding.tvEmpty.visibility = View.VISIBLE
                            binding.tvEmpty.text = getString(R.string.no_animal_data).format(mName)
                        } else {
                            binding.rvAnimal.visibility = View.VISIBLE
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

    override fun onDestroyView() {
        binding.rvAnimal.adapter = null
        super.onDestroyView()
    }
}
