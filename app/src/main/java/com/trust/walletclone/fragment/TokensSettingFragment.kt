package com.trust.walletclone.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.trust.walletclone.activity.MainActivity
import com.trust.walletclone.adapter.CoinAdapter
import com.trust.walletclone.adapter.CoinSettingAdapter
import com.trust.walletclone.databinding.FragmentTokensSettingBinding
import com.trust.walletclone.util.Coin
import com.trust.walletclone.util.DBHelper


class TokensSettingFragment : Fragment() {

    private lateinit var binding: FragmentTokensSettingBinding
    var coinList :ArrayList<Coin> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTokensSettingBinding.inflate(inflater)
        initView()
        return binding.root
    }


    fun initView(){
        coinList = DBHelper(requireContext()).readAllCoins()

        Log.e("coins", coinList.toString())
        binding.listTokens.layoutManager = LinearLayoutManager(context)
        var coinAdapter = CoinSettingAdapter(coinList, context)
        coinAdapter.onItemClick={ coin->
            val fragment = TokenFragment(coin)
            (activity as MainActivity).replaceFragments(fragment)
        }
        binding.listTokens.adapter = coinAdapter
        binding.toolbar.btnBack.setOnClickListener {
            val fragment = WalletFragment()
            val mainActivity = activity as MainActivity
            mainActivity.replaceFragments(fragment)
        }
    }
}