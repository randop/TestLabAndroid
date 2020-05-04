package com.randolphledesma.TestLab.model

import androidx.recyclerview.widget.DiffUtil

data class MenuItem(
    val id: Int,
    val title: String,
    val subtitle: String,
    val description: String,
    val isSpecial: Boolean
)

object MenuItemDiff : DiffUtil.ItemCallback<MenuItem>() {
    override fun areItemsTheSame(oldItem: MenuItem, newItem: MenuItem) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: MenuItem, newItem: MenuItem) = oldItem == newItem
}