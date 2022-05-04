package com.trust.walletclone.fragment

import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.logging.type.HttpRequest
import com.trust.walletclone.activity.MainActivity
import com.trust.walletclone.databinding.FragmentImportPhraseBinding
import com.trust.walletclone.util.Coin
import com.trust.walletclone.util.Global
import wallet.core.jni.CoinType
import wallet.core.jni.HDWallet
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URI
import java.net.URL
import java.net.URLEncoder


class ImportPhraseFragment : Fragment() {

    private lateinit var binding: FragmentImportPhraseBinding
    var coinList :ArrayList<Coin> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImportPhraseBinding.inflate(inflater)
        initView()
        return binding.root
    }


    fun initView(){
        binding.btnImport.setOnClickListener {
            if(!binding.seed.text!!.isEmpty()){
                var seed = binding.seed.text.toString()
                Global().save(requireContext(),seed,"import")
                var intent = Intent(requireActivity(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }
        binding.btnPaste.setOnClickListener {
            val seed = (requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).primaryClip?.getItemAt(0)?.text.toString()
            binding.seed.setText(seed)
            binding.btnPaste.visibility = View.GONE
        }
        binding.seed.addTextChangedListener { watcher->
            if(watcher!!.isNotEmpty()){
                binding.btnPaste.visibility = View.GONE
            }
            else{
                binding.btnPaste.visibility = View.VISIBLE
            }
        }
    }
}