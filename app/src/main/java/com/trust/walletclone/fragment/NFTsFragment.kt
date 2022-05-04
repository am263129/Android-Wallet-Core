package com.trust.walletclone.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trust.walletclone.databinding.FragmentNftsBinding
import com.trust.walletclone.util.Coin

class NFTsFragment : Fragment() {

    private lateinit var binding: FragmentNftsBinding
    var coinList :ArrayList<Coin> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNftsBinding.inflate(inflater)
        initView()
        return binding.root
    }


    fun initView(){

    }
}