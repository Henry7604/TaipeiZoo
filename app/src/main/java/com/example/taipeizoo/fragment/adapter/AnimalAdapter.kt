package com.example.taipeizoo.fragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.taipeizoo.R
import com.example.taipeizoo.databinding.ItemInfoBinding
import com.example.taipeizoo.fragment.InfoBottomSheetFragment
import com.example.taipeizoo.network.model.Animal
import com.example.taipeizoo.replaceHttp

class AnimalAdapter(private val fragment: Fragment): PagingDataAdapter<Animal, AnimalAdapter.AnimalViewHolder>(AnimalComparator) {
    object AnimalComparator : DiffUtil.ItemCallback<Animal>() {
        override fun areItemsTheSame(oldItem: Animal, newItem: Animal): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Animal, newItem: Animal): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val binding = ItemInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it)
        }
    }

    override fun onViewRecycled(holder: AnimalViewHolder) {
        super.onViewRecycled(holder)
        Glide.with(fragment).clear(holder.binding.ivImg)
    }

    inner class AnimalViewHolder(val binding: ItemInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Animal) {
            binding.apply {
                Glide.with(fragment)
                    .load(item.pic01Url.replaceHttp)
                    .placeholder(R.drawable.logo)
                    .into(ivImg)

                tvName.text = item.nameCh
                tvInfo.text = item.feature
                tvMemo.visibility = View.INVISIBLE

                root.setOnClickListener {
                    val dialog = InfoBottomSheetFragment.newInstance(item.generateZooItem())
                    dialog.show(fragment.childFragmentManager, dialog.tag)
                }
            }
        }
    }
}