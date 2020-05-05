package com.randolphledesma.testlab.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.view.doOnNextLayout
import com.google.android.material.snackbar.Snackbar
import com.randolphledesma.testlab.R
import com.randolphledesma.testlab.databinding.ActivityMainBinding
import com.randolphledesma.testlab.model.MenuItem
import com.randolphledesma.testlab.util.contentView
import com.randolphledesma.testlab.util.edit
import com.randolphledesma.testlab.util.set
import java.text.SimpleDateFormat
import java.util.*
import kotlin.test.assertNotNull

class MainActivity : AppCompatActivity() {
    companion object {
        private const val REQUEST_SCAN_RESULT = 1
    }

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

                Snackbar.make(it, "Current Date and Time is: $current", Snackbar.LENGTH_SHORT).show()

                sharedPref.edit {
                    set("foo" to current.toString())
                }
            }

            buttonCountries.setOnClickListener {
                startCountriesActivity(it)
            }

            buttonScan.setOnClickListener {
                startScanActivity(it)
            }

            buttonWeather.setOnClickListener {
                startWeatherActivity(it)
            }

            buttonBottomSheet.setOnClickListener {
                val verseSheet = VerseSheetFragment()
                verseSheet.show(supportFragmentManager, VerseSheetFragment.FRAGMENT_TAG)
            }

            buttonQrcode.setOnClickListener {
                val qrSheet = QrSheetFragment.newInstance("https://github.com/randop")
                qrSheet.show(supportFragmentManager, QrSheetFragment.FRAGMENT_TAG)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_SCAN_RESULT) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                data.apply {
                    val qrScanned = getStringExtra(ScanActivity.RESULT_KEY)
                    val qrSheet = QrSheetFragment.newInstance(qrScanned)
                    qrSheet.show(supportFragmentManager, QrSheetFragment.FRAGMENT_TAG)
                }
            }
        }
    }

    /** Called when the user taps the Send button */
    fun startWeatherActivity(view: View) {
        val intent = Intent(this, WeatherActivity::class.java)
        startActivity(intent)
    }

    fun startCountriesActivity(view: View) {
        val intent = Intent(this, CountriesActivity::class.java)
        startActivity(intent)
    }

    fun startScanActivity(view: View) {
        val intent = Intent(this, ScanActivity::class.java)
        startActivityForResult(intent, REQUEST_SCAN_RESULT)
    }
}