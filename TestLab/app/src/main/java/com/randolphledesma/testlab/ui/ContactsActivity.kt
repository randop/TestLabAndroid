package com.randolphledesma.testlab.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil.setContentView
import com.randolphledesma.testlab.R
import com.randolphledesma.testlab.databinding.ActivityContactsBinding

class ContactsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.contacts_activity_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView<ActivityContactsBinding>(this, R.layout.activity_contacts)
    }
}
