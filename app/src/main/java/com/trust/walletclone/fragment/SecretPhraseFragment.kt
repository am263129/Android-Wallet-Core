package com.trust.walletclone.fragment

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import cash.z.ecc.android.bip39.Mnemonics
import cash.z.ecc.android.bip39.Mnemonics.MnemonicCode
import cash.z.ecc.android.bip39.toSeed
import com.google.android.material.snackbar.Snackbar
import com.trust.walletclone.activity.PhraseActivity
import com.trust.walletclone.adapter.SeedAdapter
import com.trust.walletclone.databinding.FragmentSecretPhraseBinding
import java.util.*
import kotlin.collections.ArrayList


class SecretPhraseFragment : Fragment() {

    private lateinit var binding: FragmentSecretPhraseBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSecretPhraseBinding.inflate(inflater)
        initView()
        return binding.root
    }
    fun initView(){
        var seedJoiner: StringJoiner = StringJoiner(" ")
        val mnemonicCode: MnemonicCode = MnemonicCode(Mnemonics.WordCount.COUNT_12)
        val seed: ByteArray = mnemonicCode.toSeed()
        var seedArray : ArrayList<String> = ArrayList()
        for (word in mnemonicCode) {
            seedJoiner.add(word)
            seedArray.add(word)
        }
        binding.seedContainer.layoutManager = GridLayoutManager(context, 3)
        var seedAdapter:SeedAdapter = SeedAdapter(seedArray)
        binding.seedContainer.adapter = seedAdapter

        binding.btnCopy.setOnClickListener {
            val phraseActivity = activity as PhraseActivity
            val clipboardManager = phraseActivity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("text", seedJoiner.toString())
            clipboardManager.setPrimaryClip(clipData)
            Snackbar.make(binding.root, "Copied.  ".plus( seedJoiner.toString()), Snackbar.LENGTH_SHORT).show()
        }
        binding.btnContinue.setOnClickListener {
            (activity as PhraseActivity).replaceFragments(VerifyPhraseFragment(mnemonicCode))
        }
    }

}