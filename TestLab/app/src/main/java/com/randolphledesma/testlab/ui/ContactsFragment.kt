package com.randolphledesma.testlab.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.randolphledesma.testlab.adapters.ContactAdapter
import com.randolphledesma.testlab.databinding.FragmentContactsBinding
import com.randolphledesma.testlab.model.ContactListViewModel
import com.randolphledesma.testlab.util.InjectorUtils

class ContactsFragment : Fragment() {

    private val viewModel: ContactListViewModel by viewModels {
        InjectorUtils.provideContactListViewModelFactory(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentContactsBinding.inflate(inflater, container, false)
        context ?: return binding.root

        val adapter = ContactAdapter()
        binding.contactList.adapter = adapter
        subscribeUi(adapter)
        return binding.root
    }

    private fun subscribeUi(adapter: ContactAdapter) {
        viewModel.contacts.observe(viewLifecycleOwner) { contacts ->
            adapter.submitList(contacts)
        }
    }
}
