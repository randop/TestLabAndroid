package com.randolphledesma.testlab.ui

import android.Manifest
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Vibrator
import androidx.appcompat.app.AppCompatActivity
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import kotlinx.android.synthetic.main.activity_scan.*
import com.google.gson.JsonElement
import com.google.gson.GsonBuilder
import com.randolphledesma.testlab.R


class ScanActivity : AppCompatActivity() {
    companion object {
        const val RESULT_KEY = "qr_scanned"

        fun newInstance(context: Context) = Intent(context, ScanActivity::class.java)
    }

    private var isTorch = false
    private val REQUEST_PERMISSION_CODE = 777

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkPermissions()

        val window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        }

        setContentView(R.layout.activity_scan)

        barcode_view.decodeContinuous(callback)

        img_flash.setOnClickListener {
            if (isTorch) {
                isTorch = false
                img_flash.setImageResource(R.drawable.ic_flash_off)
            } else {
                isTorch = true
                img_flash.setImageResource(R.drawable.ic_flash_on)
            }
            barcode_view.setTorch(isTorch)
        }
    }

    override fun onResume() {
        barcode_view.resume()
        super.onResume()
    }

    override fun onPause() {
        barcode_view.pause()
        super.onPause()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_CODE) {
            var granted = true
            for (i in permissions.indices ){
                if (grantResults.get(i) ==  PackageManager.PERMISSION_DENIED) {
                    granted = false
                }
            }
            if (granted) {
                recreate()
            } else {
                finish()
            }
        }
    }

    private fun checkPermissions() {
        var permissions = arrayListOf<String>()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.CAMERA)
            }
            if (permissions.isNotEmpty()) {
                requestPermissions(permissions.toTypedArray(), REQUEST_PERMISSION_CODE)
            }
        }
    }

    private val callback = object : BarcodeCallback {
        override fun possibleResultPoints(resultPoints: MutableList<ResultPoint>?) {

        }

        override fun barcodeResult(result: BarcodeResult?) {
            if (result!!.text != null){
                val gson = GsonBuilder().setPrettyPrinting().create()
                val json = gson.toJsonTree(result.result)
                val jsonInString = gson.toJson(json)
                //println(result.resultMetadata)
                //println("Result: ${result.text.toString()}")
                val vibrator = application.getSystemService(Service.VIBRATOR_SERVICE) as Vibrator
                vibrator.vibrate(100)
                returnResult(result.text.toString())
            }
        }
    }

    fun returnResult(value: String) {
        val data = Intent().apply {
            putExtra(RESULT_KEY, value)
        }
        setResult(RESULT_OK, data)
        finish()
    }
}
