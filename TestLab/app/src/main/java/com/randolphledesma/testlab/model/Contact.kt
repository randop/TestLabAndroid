package com.randolphledesma.testlab.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class Contact(
    @PrimaryKey @ColumnInfo(name = "id") val contactId: String,
    val name: String,
    val phone: String
) {
    override fun toString() = name
}