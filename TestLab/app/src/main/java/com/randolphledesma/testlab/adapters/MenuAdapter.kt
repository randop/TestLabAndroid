package com.randolphledesma.testlab.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.randolphledesma.testlab.databinding.MenuItemBinding
import com.randolphledesma.testlab.model.MenuItem
import com.randolphledesma.testlab.model.MenuItemDiff


class MenuAdapter(
    private val onClick: MenuItemViewClick
) : ListAdapter<MenuItem, MenuViewHolder>(MenuItemDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        return MenuViewHolder(
            MenuItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }
}

interface MenuItemViewClick {
    fun onClick(view: View, id: Int, title: String)
}

class MenuViewHolder(
    private val binding: MenuItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(menuItem: MenuItem, onClick: MenuItemViewClick) {
        binding.run {
            this.item = menuItem
            clickHandler = onClick
            executePendingBindings()
        }
    }
}
