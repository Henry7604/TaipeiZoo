package com.henry.zoo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 *
 * @author Henry
 *
 * @param T 指定泛型
 * @param layoutID layout id
 * @param adapterInterface 實作item click及bind
 */
open class BaseRecyclerViewAdapter<T>(private val layoutID: Int, protected val adapterInterface: AdapterInterface<T>) :
    RecyclerView.Adapter<BaseRecyclerViewAdapter.ViewHolder<T>>() {
    protected val mList = ArrayList<T>()

    open fun addList(list: List<T>) {
        mList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<T> {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : ViewDataBinding =
            DataBindingUtil.inflate(layoutInflater, layoutID, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder<T>, position: Int) {
        holder.bind(mList[position], adapterInterface)
        holder.binding.root.setOnClickListener {
            adapterInterface.onItemClick(mList[position])
        }
    }

    class ViewHolder<T>(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: T, adapterInterface: AdapterInterface<T>) {
            adapterInterface.bindData(binding, data)
            binding.executePendingBindings()
        }
    }
}