package com.trust.walletclone.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trust.walletclone.R
import com.trust.walletclone.databinding.FragmentDiscoverBinding
import com.trust.walletclone.databinding.FragmentWalletBinding


class DiscoverFragment : Fragment() {
    private lateinit var binding: FragmentDiscoverBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDiscoverBinding.inflate(inflater)
        initView()
        // Inflate the layout for this fragment
        return binding.root
    }

    fun initView(){

    }
}