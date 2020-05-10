package com.randolphledesma.testlab.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.randolphledesma.testlab.databinding.ListItemContactBinding
import com.randolphledesma.testlab.model.Contact

class ContactAdapter : ListAdapter<Contact, RecyclerView.ViewHolder>(ContactDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ContactViewHolder(
            ListItemContactBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val contact = getItem(position)
        (holder as ContactViewHolder).bind(contact)
    }

    class ContactViewHolder(
        private val binding: ListItemContactBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                binding.contact?.let { contact ->
                    //navigateToPlant(contact, it)
                    println(contact)
                }
            }
        }

        fun bind(item: Contact) {
            binding.apply {
                contact = item
                executePendingBindings()
            }
        }
    }
}

private class ContactDiffCallback : DiffUtil.ItemCallback<Contact>() {

    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem.contactId == newItem.contactId
    }

    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem == newItem
    }
}