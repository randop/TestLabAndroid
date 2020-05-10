package com.randolphledesma.testlab.db

class ContactRepository private constructor(private val contactDao: ContactDao) {

    fun getContacts() = contactDao.getAll()

    fun getContact(contactId: String) = contactDao.getContact(contactId)

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: ContactRepository? = null

        fun getInstance(contactDao: ContactDao) =
            instance ?: synchronized(this) {
                instance ?: ContactRepository(contactDao).also { instance = it }
            }
    }
}