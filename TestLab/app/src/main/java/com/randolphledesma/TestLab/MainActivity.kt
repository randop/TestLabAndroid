package com.randolphledesma.TestLab

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.view.doOnNextLayout
import com.google.android.material.snackbar.Snackbar
import com.randolphledesma.TestLab.databinding.ActivityMainBinding
import com.randolphledesma.TestLab.model.MenuItem
import com.randolphledesma.TestLab.ui.MenuAdapter
import com.randolphledesma.TestLab.ui.MenuItemViewClick
import com.randolphledesma.TestLab.util.contentView
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.test.assertNotNull

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by contentView(R.layout.activity_main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        assertNotNull(sharedPref, "SharedPreferences Context Failed")
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        val menus = listOf(
            MenuItem(1,"First","First Subtitle","Description",false),
            MenuItem(2,"Second","Second Subtitle","Description",false)
        )

        val onMenuItemClick: MenuItemViewClick = object : MenuItemViewClick {
            override fun onClick(view: View, id: Int, title: String) {
                Snackbar.make(view, title, Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.apply {
            promotionGrid.apply {
                adapter = MenuAdapter(onMenuItemClick).apply {
                    doOnNextLayout {
                        submitList(menus)
                    }
                }
            }

            button.setOnClickListener {
                println(sharedPref.getString("foo", ""))
                val current = SimpleDateFormat("MM dd, yyyy HH:mm:ss", Locale.getDefault()).format( Date() )
                Toast.makeText(this@MainActivity, "Current Date and Time is: $current", Toast.LENGTH_SHORT).show()

                sharedPref.edit {
                    set("foo" to current.toString())
                }
            }
        }
    }

    /** Called when the user taps the Send button */
    fun sendMessage(view: View) {
        val message = editText.text.toString()
        val intent = Intent(this, WeatherActivity::class.java)
        startActivity(intent)
    }

    fun startCountriesActivity(view: View) {
        val intent = Intent(this@MainActivity, CountriesActivity::class.java)
        startActivity(intent)
    }
}