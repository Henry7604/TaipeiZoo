package com.example.taipeizoo.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.taipeizoo.DialogUtility
import com.example.taipeizoo.ParkDetailActivity
import com.example.taipeizoo.base.BaseViewBindingListAdapter
import com.example.taipeizoo.R
import com.example.taipeizoo.databinding.FragmentParkIntroBinding
import com.example.taipeizoo.databinding.ItemInfoBinding
import com.example.taipeizoo.network.NetworkResponse
import com.example.taipeizoo.network.model.ParkIntro
import com.example.taipeizoo.replaceHttp
import com.example.taipeizoo.viewModel.ParkIntroViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ParkIntroFragment : BaseFragment<FragmentParkIntroBinding>() {
    private val mViewModel: ParkIntroViewModel by viewModels()
    private val mAdapter by lazy {
        object : BaseViewBindingListAdapter<ParkIntro, ItemInfoBinding>(DefaultDiffCallback()) {
            override fun createBinding(
                inflater: LayoutInflater,
                parent: ViewGroup,
                viewType: Int
            ): ItemInfoBinding {
                return ItemInfoBinding.inflate(inflater, parent, false)
            }

            override fun onBindItem(binding: ItemInfoBinding, item: ParkIntro, position: Int) {
                binding.apply {
                    Glide.with(this@ParkIntroFragment).load(item.ePicUrl.replaceHttp).placeholder(R.drawable.logo)
                        .into(ivImg)
                    tvName.text = item.eName
                    tvInfo.text = item.eInfo
                    tvMemo.text = if (item.eMemo.isNullOrEmpty()) getString(R.string.memo_empty) else item.eMemo
                    root.setOnClickListener {
                        val intent = Intent(requireActivity(), ParkDetailActivity::class.java)
                        intent.putExtra(ParkDetailActivity.INTENT_IMAGE_URL, item.ePicUrl)
                        intent.putExtra(ParkDetailActivity.INTENT_NAME, item.eName)
                        intent.putExtra(ParkDetailActivity.INTENT_INFO, item.eInfo)
                        intent.putExtra(ParkDetailActivity.INTENT_CATEGORY, item.eCategory)
                        intent.putExtra(ParkDetailActivity.INTENT_URL, item.eUrl)
                        startActivity(intent)
                    }
                }
            }
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentParkIntroBinding {
        return FragmentParkIntroBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvExhibit.adapter = mAdapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.parkIntroListStateFlow.collect {
                    it?.apply {
                        when (this) {
                            is NetworkResponse.Success -> {
                                val data = this.body
                                mAdapter.submitList(data.result?.results?: listOf())
                            }
                            is NetworkResponse.ApiError -> {
                                DialogUtility.defaultApiError(requireActivity(), this.errorMsg) {
                                    mViewModel.getParkIntroList()
                                }
                            }
                            is NetworkResponse.NetworkError -> {
                                DialogUtility.defaultNetworkError(requireActivity()) {
                                    mViewModel.getParkIntroList()
                                }
                            }
                            is NetworkResponse.UnknownError -> {
                                DialogUtility.defaultApiError(requireActivity(), null) {
                                    mViewModel.getParkIntroList()
                                }
                            }
                        }
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.loadingState.collect {
                    if (it) {
                        showProgress()
                    } else {
                        hideProgress()
                    }
                }
            }
        }
        mViewModel.getParkIntroList()
    }
}
