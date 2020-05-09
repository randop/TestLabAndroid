package com.randolphledesma.testlab.util

import android.content.Context
import androidx.fragment.app.Fragment
import com.randolphledesma.testlab.db.AppDatabase
import com.randolphledesma.testlab.db.ContactRepository

object InjectorUtils {
    private fun getContactRepository(context: Context): ContactRepository {
        return ContactRepository.getInstance(AppDatabase.getInstance(context.applicationContext).contactDao())
    }

    fun provideContactListViewModelFactory(fragment: Fragment): PlantListViewModelFactory {
        val repository = getPlantRepository(fragment.requireContext())
        return PlantListViewModelFactory(repository, fragment)
    }

    fun providePlantDetailViewModelFactory(
        context: Context,
        plantId: String
    ): PlantDetailViewModelFactory {
        return PlantDetailViewModelFactory(getPlantRepository(context),
            getGardenPlantingRepository(context), plantId)
    }
}