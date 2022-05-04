package com.trust.walletclone.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.trust.walletclone.activity.WalletImportActivity
import com.trust.walletclone.adapter.CoinAdapter
import com.trust.walletclone.databinding.FragmentImportWalletSelectBinding
import com.trust.walletclone.util.Coin
import com.trust.walletclone.util.DBHelper


class WalletImportSelectFragment : Fragment() {

    private lateinit var binding: FragmentImportWalletSelectBinding
    var coinList :ArrayList<Coin> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImportWalletSelectBinding.inflate(inflater)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        initView()
    }

    fun initView(){
        coinList  = DBHelper(requireContext()).readAllCoins()

        binding.listTokens.layoutManager = LinearLayoutManager(context)
        var coinAdapter = CoinAdapter(coinList, context)
        coinAdapter.onItemClick={ coin->
            val fragment = WalletImportFragment(coin)
            (activity as WalletImportActivity).replaceFragments(fragment)
        }
        binding.listTokens.adapter = coinAdapter
        binding.toolbar.btnBack.setOnClickListener {
            requireActivity().finish()
        }
        binding.toolbar.title.text = "Import"
    }
}