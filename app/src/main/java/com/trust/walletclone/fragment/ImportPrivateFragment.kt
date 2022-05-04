package com.trust.walletclone.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.trust.walletclone.databinding.FragmentImportPrivatekeyBinding
import com.trust.walletclone.util.Coin


class ImportPrivateFragment : Fragment() {

    private lateinit var binding: FragmentImportPrivatekeyBinding
    var coinList :ArrayList<Coin> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImportPrivatekeyBinding.inflate(inflater)
        initView()
        return binding.root
    }


    fun initView(){
    }
}