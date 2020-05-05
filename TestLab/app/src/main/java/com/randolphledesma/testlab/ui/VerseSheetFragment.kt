package com.randolphledesma.testlab.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.randolphledesma.testlab.databinding.FragmentVerseSheetBinding

class VerseSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentVerseSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVerseSheetBinding.inflate(inflater, container, false).apply {
            buttonGithub.setOnClickListener {
                val openURL = Intent(Intent.ACTION_VIEW)
                openURL.data = Uri.parse("https://github.com/randop")
                startActivity(openURL)
            }

            buttonThanks.setOnClickListener {
                val openURL = Intent(Intent.ACTION_VIEW)
                openURL.data = Uri.parse("https://www.bible.com/bible/1/JHN.3.16.KJV")
                startActivity(openURL)
            }
        }
        return binding.root
    }

    companion object {
        const val FRAGMENT_TAG = "verse_sheet_fragment_tag"
    }
}