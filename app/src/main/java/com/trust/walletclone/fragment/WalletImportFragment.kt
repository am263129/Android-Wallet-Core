package com.trust.walletclone.fragment

import ImportJsonFragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.trust.walletclone.activity.MainActivity
import com.trust.walletclone.activity.PhraseActivity
import com.trust.walletclone.adapter.CoinAdapter
import com.trust.walletclone.databinding.FragmentImportWalletBinding
import com.trust.walletclone.databinding.FragmentImportWalletSelectBinding
import com.trust.walletclone.databinding.FragmentTokenListBinding
import com.trust.walletclone.util.Coin


class WalletImportFragment(var coin:Coin) : Fragment() {

    private lateinit var binding: FragmentImportWalletBinding
    var coinList :ArrayList<Coin> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImportWalletBinding.inflate(inflater)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        initView()
    }


    fun initView(){
        binding.toolbar.btnBack.setOnClickListener {
            val fm: FragmentManager = requireActivity().supportFragmentManager
            fm.popBackStackImmediate()
        }
        binding.viewPager.adapter = SimpleFragmentPagerAdapter( childFragmentManager)
        binding.importTab.setupWithViewPager(binding.viewPager)
        binding.toolbar.title.text = "Import ${coin.name}"
    }

    private inner class SimpleFragmentPagerAdapter constructor(fm: FragmentManager) :
        FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        private val tabTitles = arrayOf("PHRASE", "KEYSTORE JSON", "PRIVATE KEY", "ADDRESS")
        private val mFragment = arrayOf(ImportPhraseFragment(), ImportJsonFragment(), ImportPrivateFragment(), ImportAddressFragment())

        override fun getItem(position: Int): Fragment {
            return mFragment[position]
        }

        override fun getCount(): Int {
            return mFragment.size
        }

        override fun getPageTitle(position: Int): CharSequence {
            return tabTitles[position]
        }
    }
}