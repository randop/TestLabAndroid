package com.randolphledesma.TestLab.ui

import android.Manifest
import android.app.Activity
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Vibrator
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.randolphledesma.TestLab.R
import com.randolphledesma.TestLab.util.Utility.KEY_RESULT
import kotlinx.android.synthetic.main.activity_scan.*

class ScanActivity : AppCompatActivity() {
    private var isTorch = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), 1)
            }
        }

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

    private val callback = object : BarcodeCallback {
        override fun possibleResultPoints(resultPoints: MutableList<ResultPoint>?) {

        }

        override fun barcodeResult(result: BarcodeResult?) {
            if (result!!.text != null){
                //println(result.resultMetadata)
                println("Result: ${result.text.toString()}")
                val vibrator = application.getSystemService(Service.VIBRATOR_SERVICE) as Vibrator
                vibrator.vibrate(100)
                finish()
            }
        }
    }
}
