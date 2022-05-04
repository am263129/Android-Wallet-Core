package com.trust.walletclone.fragment

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.snackbar.Snackbar
import com.trust.walletclone.R
import com.trust.walletclone.activity.MainActivity
import com.trust.walletclone.activity.ReceiveTokenActivity
import com.trust.walletclone.databinding.FragmentStakeBinding
import com.trust.walletclone.databinding.FragmentTokenBinding
import com.trust.walletclone.dialog.*
import com.trust.walletclone.util.Coin
import java.io.IOException
import java.io.InputStream


class TokenStakeStatusFragment(private var coin: Coin) : Fragment() {

    private lateinit var binding: FragmentStakeBinding
    var coinList: ArrayList<Coin> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStakeBinding.inflate(inflater)
        initView()
        return binding.root
    }


    fun initView() {
        var loading = LoadingDialog()
        binding.toolbar.title.text = "${coin.name} (${coin.symbol})"
        binding.btnStake.setOnClickListener {
            loading.displayLoadingWithText(context, "", false)
            Handler().postDelayed(Runnable {
                loading.hideLoading()
                val fragment = TokenStakeFragment(coin)
                (activity as MainActivity).replaceFragments(fragment)
            }, 3000)
        }
        binding.btnUnstake.setOnClickListener {
            loading.displayLoadingWithText(context, "", false)
            Handler().postDelayed(Runnable {
                loading.hideLoading()
                val snack = Snackbar.make(binding.root, getString(R.string.lbl_notavailable), Snackbar.LENGTH_INDEFINITE)
                snack.show()
            }, 3000)
        }
        binding.toolbar.btnBack.setOnClickListener {
            val fm: FragmentManager = requireActivity().supportFragmentManager
            fm.popBackStackImmediate()
        }
    }
}