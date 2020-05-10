package com.randolphledesma.testlab.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.randolphledesma.testlab.db.AppDatabase
import com.randolphledesma.testlab.model.Contact
import com.randolphledesma.testlab.util.CONTACT_DATA_FILENAME
import kotlinx.coroutines.coroutineScope

class SeedDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = coroutineScope {
        Log.i(TAG, "Running migration")
        try {
            applicationContext.assets.open(CONTACT_DATA_FILENAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val contactType = object : TypeToken<List<Contact>>() {}.type
                    val contactList: List<Contact> = Gson().fromJson(jsonReader, contactType)

                    val database = AppDatabase.getInstance(applicationContext)
                    database.contactDao().insertAll(contactList)

                    Result.success()
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }

    companion object {
        private val TAG = SeedDatabaseWorker::class.java.simpleName
    }
}