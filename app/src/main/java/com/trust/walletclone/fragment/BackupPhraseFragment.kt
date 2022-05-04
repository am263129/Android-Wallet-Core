package com.trust.walletclone.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import com.trust.walletclone.R
import com.trust.walletclone.activity.BackupActivity
import com.trust.walletclone.activity.PhraseActivity
import com.trust.walletclone.databinding.DialogLegalBinding
import com.trust.walletclone.databinding.FragmentBackupPhraseBinding

class BackupPhraseFragment : Fragment() {

    private lateinit var binding: FragmentBackupPhraseBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBackupPhraseBinding.inflate(inflater)
        initView()
        return binding.root
    }

    private fun initView() {
        binding.containerOne.setOnClickListener { binding.chkOne.performClick() }
        binding.containerTwo.setOnClickListener { binding.chkTwo.performClick() }
        binding.containerThree.setOnClickListener { binding.chkThree.performClick() }

        binding.chkOne.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                binding.descOne.setTextColor(
                    ResourcesCompat.getColor(
                        resources,
                        R.color.primary,
                        null
                    )
                )
                binding.containerOne.setBackgroundResource(R.drawable.rounded_border_black)
            } else {
                binding.descOne.setTextColor(
                    ResourcesCompat.getColor(
                        resources,
                        R.color.gray800,
                        null
                    )
                )
                binding.containerOne.setBackgroundResource(R.drawable.rounded_border)
            }
            updateUi()
        }
        binding.chkTwo.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                binding.descTwo.setTextColor(
                    ResourcesCompat.getColor(
                        resources,
                        R.color.primary,
                        null
                    )
                )
                binding.containerTwo.setBackgroundResource(R.drawable.rounded_border_black)
            } else {
                binding.descTwo.setTextColor(
                    ResourcesCompat.getColor(
                        resources,
                        R.color.gray800,
                        null
                    )
                )
                binding.containerTwo.setBackgroundResource(R.drawable.rounded_border)
            }
            updateUi()
        }
        binding.chkThree.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                binding.descThree.setTextColor(
                    ResourcesCompat.getColor(
                        resources,
                        R.color.primary,
                        null
                    )
                )
                binding.containerThree.setBackgroundResource(R.drawable.rounded_border_black)
            } else {
                binding.descThree.setTextColor(
                    ResourcesCompat.getColor(
                        resources,
                        R.color.gray800,
                        null
                    )
                )
                binding.containerThree.setBackgroundResource(R.drawable.rounded_border)
            }
            updateUi()
        }
        binding.btnContinue.setOnClickListener {
            if (binding.chkOne.isChecked && binding.chkTwo.isChecked && binding.chkThree.isChecked) {

                val fragment = SecretPhraseFragment()
                val phraseActivity = activity as PhraseActivity
                phraseActivity.replaceFragments(fragment)
            }
        }
    }


    fun updateUi() {
        binding.btnContinue.isEnabled =
            binding.chkOne.isChecked && binding.chkTwo.isChecked && binding.chkThree.isChecked
        if (binding.chkOne.isChecked && binding.chkTwo.isChecked && binding.chkThree.isChecked) {
            binding.btnContinue.setBackgroundColor(
                ResourcesCompat.getColor(
                    resources,
                    R.color.primary,
                    null
                )
            )
        } else {
            binding.btnContinue.setBackgroundColor(
                ResourcesCompat.getColor(
                    resources,
                    R.color.gray,
                    null
                )
            )
        }
    }
}