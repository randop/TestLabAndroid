package com.randolphledesma.testlab.util

import android.content.Context
import androidx.fragment.app.Fragment
import com.randolphledesma.testlab.db.AppDatabase
import com.randolphledesma.testlab.db.ContactRepository
import com.randolphledesma.testlab.model.ContactListViewModelFactory

object InjectorUtils {
    private fun getContactRepository(context: Context): ContactRepository {
        return ContactRepository.getInstance(AppDatabase.getInstance(context.applicationContext).contactDao())
    }

    fun provideContactListViewModelFactory(fragment: Fragment): ContactListViewModelFactory {
        val repository = getContactRepository(fragment.requireContext())
        return ContactListViewModelFactory(repository, fragment)
    }
}