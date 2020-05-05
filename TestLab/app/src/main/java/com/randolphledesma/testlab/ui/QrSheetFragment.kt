package com.randolphledesma.testlab.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.randolphledesma.testlab.databinding.FragmentQrSheetBinding
import com.randolphledesma.testlab.util.Utility

class QrSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentQrSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQrSheetBinding.inflate(inflater, container, false).apply {
            val qrValue = arguments?.getString("qr_value")
            if (qrValue!=null) {
                val qrBitmap = Utility.generateQR(qrValue)
                imageQr.setImageBitmap(qrBitmap)
                textQr.text = qrValue
                if (qrValue.indexOf("http") == 0) {
                    textQr.setOnClickListener {
                        val openURL = Intent(Intent.ACTION_VIEW, Uri.parse(qrValue))
                        startActivity(openURL)
                    }
                }
            }
        }
        return binding.root
    }

    companion object {
        const val FRAGMENT_TAG = "qr_sheet_fragment_tag"

        @JvmStatic
        fun newInstance(value: String) = QrSheetFragment().apply {
            arguments = Bundle().apply {
                putString("qr_value", value)
            }
        }
    }
}