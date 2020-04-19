package com.randolphledesma.TestLab

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.randolphledesma.TestLab.R
import com.randolphledesma.TestLab.edit
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPref = getPreferences(Context.MODE_PRIVATE)

        setContentView(R.layout.activity_main)
        text1.text = getFilesDir().toString()

        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(Request.Method.GET, "http://httpbin.org/anything",
            Response.Listener<String> { response ->
                text1.text = response
            },
            Response.ErrorListener { error ->
                println(error.message)
            })

        button.setOnClickListener {
            println(sharedPref.getString("foo", ""))

            queue.add(stringRequest)
            val current = SimpleDateFormat("MM dd, yyyy HH:mm:ss", Locale.getDefault()).format( Date() )
            Toast.makeText(this, "Current Date and Time is: $current", Toast.LENGTH_SHORT).show()

            sharedPref.edit {
                set("foo" to current.toString())
            }
        }
    }
}