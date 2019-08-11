package com.randolphledesma.TestLab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView = findViewById<TextView>(R.id.text1)
        textView.text = ""

        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(Request.Method.GET, "http://httpbin.org/anything",
            Response.Listener<String> { response ->
                textView.text = response
            },
            Response.ErrorListener { error ->
                println("Network error happened")
                println(error)
            })

        val myButton: Button = findViewById(R.id.button)
        myButton.setOnClickListener {
            queue.add(stringRequest)
            Toast.makeText(this@MainActivity, "You clicked me.", Toast.LENGTH_SHORT).show()
        }
    }
}
