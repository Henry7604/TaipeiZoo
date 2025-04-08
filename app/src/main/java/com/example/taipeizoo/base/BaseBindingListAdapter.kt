package com.example.taipeizoo.base

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewBindingListAdapter<M, VB : ViewBinding>(
    diffCallback: DiffUtil.ItemCallback<M>
) : ListAdapter<M, BaseViewBindingListAdapter.BaseViewBindingViewHolder<VB>>(diffCallback) {

    class DefaultDiffCallback<M : Any> : DiffUtil.ItemCallback<M>() {
        override fun areItemsTheSame(oldItem: M, newItem: M): Boolean = oldItem == newItem

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: M, newItem: M) = oldItem == newItem
    }

    class BaseViewBindingViewHolder<VB : ViewBinding>(val binding: VB) : RecyclerView.ViewHolder(binding.root)

    protected abstract fun createBinding(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): VB

    protected abstract fun onBindItem(binding: VB, item: M, position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewBindingViewHolder<VB> {
        val binding = createBinding(LayoutInflater.from(parent.context), parent, viewType)
        return BaseViewBindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewBindingViewHolder<VB>, position: Int) {
        onBindItem(holder.binding, getItem(position), position)
    }
}