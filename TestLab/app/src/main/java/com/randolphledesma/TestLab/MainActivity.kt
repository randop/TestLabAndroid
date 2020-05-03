package com.randolphledesma.TestLab

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.test.assertNotNull

const val EXTRA_MESSAGE = "com.randolphledesma.TestLab.MESSAGE"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        assertNotNull(sharedPref, "SharedPreferences Context Failed")

        setContentView(R.layout.activity_main)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

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

        button3.setOnClickListener {
            val intent = Intent(this, CountriesActivity::class.java)
            startActivity(intent)
        }
    }

    /** Called when the user taps the Send button */
    fun sendMessage(view: View) {
        val message = editText.text.toString()
        val intent = Intent(this, WeatherActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, message)
        }
        startActivity(intent)
    }
}