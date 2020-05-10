package com.randolphledesma.testlab.model

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.savedstate.SavedStateRegistryOwner
import com.randolphledesma.testlab.db.ContactRepository

@Entity(tableName = "contacts")
data class Contact(
    @PrimaryKey @ColumnInfo(name = "id") val contactId: String,
    val name: String,
    val phone: String
) {
    override fun toString() = "$name : $phone"
}

class ContactListViewModel internal constructor(
    contactRepository: ContactRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val contacts: LiveData<List<Contact>> = contactRepository.getContacts()
}

class ContactListViewModelFactory(
    private val repository: ContactRepository,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return ContactListViewModel(repository, handle) as T
    }
}