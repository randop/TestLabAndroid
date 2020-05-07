package com.randolphledesma.testlab.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.randolphledesma.testlab.databinding.FragmentVerseSheetBinding
import com.randolphledesma.testlab.util.launchUrl

class VerseSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentVerseSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVerseSheetBinding.inflate(inflater, container, false).apply {
            buttonGithub.setOnClickListener {
                this@VerseSheetFragment.launchUrl("https://github.com/randop")
            }

            buttonThanks.setOnClickListener {
                this@VerseSheetFragment.launchUrl("https://www.bible.com/bible/1/JHN.3.16.KJV")
            }
        }
        return binding.root
    }

    companion object {
        const val FRAGMENT_TAG = "verse_sheet_fragment_tag"
    }
}