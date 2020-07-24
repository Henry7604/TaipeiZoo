package com.henry.zoo.adapter

import android.content.Intent
import android.net.Uri
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.henry.zoo.BR
import com.henry.zoo.R
import com.henry.zoo.database.enity.Plant
import com.henry.zoo.databinding.ItemCategoryDetailBinding
import com.henry.zoo.utility.ResourceProvider

/**
 *
 * @author Henry
 */
private const val ITEM_HEADER = 0
private const val ITEM_NORMAL = 1

class CategoryDetailAdapter(val itemListener: ItemListener<Plant>) :
    RecyclerView.Adapter<CategoryDetailAdapter.ViewHolder>() {

    private val mList = ArrayList<Plant>()

    fun addList(list: List<Plant>) {
        mList.addAll(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return if (viewType == ITEM_HEADER) {
            val binding: ViewDataBinding =
                DataBindingUtil.inflate(
                    layoutInflater,
                    R.layout.item_category_detail,
                    parent,
                    false
                )
            ViewHolder(binding)
        } else {
            val binding: ViewDataBinding =
                DataBindingUtil.inflate(
                    layoutInflater,
                    R.layout.item_plant,
                    parent,
                    false
                )
            ViewHolder(binding)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            ITEM_HEADER
        } else {
            ITEM_NORMAL
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mList[position])
        val model = mList[position]
        if (holder.binding is ItemCategoryDetailBinding) {
            val openUrl = ResourceProvider.getString(R.string.open_browser)
            val spannableString = SpannableString(openUrl)
            spannableString.setSpan(object : ClickableSpan() {
                override fun onClick(widget: View) {
                    itemListener.onItemClick(model)
                }
            }, 0, openUrl.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            holder.binding.tvOpenUrl.text = spannableString
            holder.binding.tvOpenUrl.movementMethod = LinkMovementMethod.getInstance()
        } else {
            holder.binding.root.setOnClickListener {
                itemListener.onItemClick(model)
            }
        }
    }

    class ViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Any) {
            binding.setVariable(BR.model, data)
            binding.executePendingBindings()
        }
    }
}