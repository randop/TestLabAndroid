package com.randolphledesma.testlab.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.randolphledesma.testlab.model.Contact

@Dao
interface ContactDao {
    @Query("SELECT * FROM contacts ORDER BY name")
    fun getAll(): LiveData<List<Contact>>

    @Query("SELECT * FROM contacts WHERE id = :contactId")
    fun getContact(contactId: String): LiveData<Contact>

    @Query("SELECT * FROM contacts WHERE id IN (:contactIds)")
    fun loadAllByIds(contactIds: IntArray): List<Contact>

    @Query("SELECT * FROM contacts WHERE name LIKE :name LIMIT 1")
    fun findByName(first: String, last: String): Contact

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(contacts: List<Contact>)

    @Delete
    fun delete(user: Contact)
}