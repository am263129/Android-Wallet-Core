package com.trust.walletclone.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.trust.walletclone.activity.MainActivity
import com.trust.walletclone.databinding.FragmentWalletBinding
import com.trust.walletclone.dialog.*


class WalletFragment : Fragment() {

    private lateinit var binding: FragmentWalletBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWalletBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    fun initView(){
        binding.viewPager.adapter = SimpleFragmentPagerAdapter(childFragmentManager)
        binding.tabLayout.setupWithViewPager(binding.viewPager)

        binding.btnTokenSetting.setOnClickListener {
            val fragment = TokensSettingFragment()
            val mainActivity = activity as MainActivity
            mainActivity.replaceFragments(fragment)
        }
        binding.btnNotification.setOnClickListener {
            NotificationDialog().show(childFragmentManager, "NotificationDialog")
        }
        binding.btnSend.setOnClickListener {
            SendTokenSelectDialog().show(childFragmentManager, "SendingDialog")
        }
        binding.btnReceive.setOnClickListener {
            ReceiveTokenSelectDialog().show(childFragmentManager, "ReceiveDialog")
        }
        binding.btnBuy.setOnClickListener {
            BuyDialog().show(childFragmentManager, "BuyDialog")
        }
        binding.btnSwap.setOnClickListener {
            SwapDialog().show(childFragmentManager, "SwapDialog")
        }
    }

    override fun onResume() {
        super.onResume()
        initView()
    }

    private inner class SimpleFragmentPagerAdapter constructor(fm: FragmentManager) :
        FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        private val tabTitles = arrayOf("Tokens", "NFTs")
        private val mFragment = arrayOf(TokenListFragment(), NFTsFragment())

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