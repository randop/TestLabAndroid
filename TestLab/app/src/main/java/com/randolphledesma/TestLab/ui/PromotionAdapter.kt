package com.randolphledesma.TestLab.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.randolphledesma.TestLab.databinding.PromotionItemBinding
import com.randolphledesma.TestLab.models.PromotionItemViewModel


class PromotionAdapter(private val items: List<PromotionItemViewModel>) : RecyclerView.Adapter<PromotionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PromotionItemBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    inner class ViewHolder(val binding: PromotionItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PromotionItemViewModel) {
            binding.item = item
            binding.executePendingBindings()
        }
    }
}