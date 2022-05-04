package com.trust.walletclone.fragment

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.trust.walletclone.R
import com.trust.walletclone.activity.MainActivity
import com.trust.walletclone.activity.ReceiveTokenActivity
import com.trust.walletclone.databinding.FragmentStakeBinding
import com.trust.walletclone.databinding.FragmentStakeTokenBinding
import com.trust.walletclone.databinding.FragmentTokenBinding
import com.trust.walletclone.dialog.*
import com.trust.walletclone.util.Coin
import java.io.IOException
import java.io.InputStream


class TokenStakeFragment(private var coin: Coin) : Fragment() {

    private lateinit var binding: FragmentStakeTokenBinding
    var coinList: ArrayList<Coin> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStakeTokenBinding.inflate(inflater)
        initView()
        return binding.root
    }


    fun initView() {
        binding.toolbar.title.text = "${coin.name} (${coin.symbol})"
        binding.toolbar.btnBack.setOnClickListener {
            val fm: FragmentManager = requireActivity().supportFragmentManager
            fm.popBackStackImmediate()
        }
    }
}