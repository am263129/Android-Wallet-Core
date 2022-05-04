package com.trust.walletclone.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.trust.walletclone.R
import com.trust.walletclone.adapter.CoinAdapter
import com.trust.walletclone.databinding.ActivityMainBinding
import com.trust.walletclone.databinding.ActivityPasswordBinding
import com.trust.walletclone.fragment.DappFragment
import com.trust.walletclone.fragment.DiscoverFragment
import com.trust.walletclone.fragment.SettingFragment
import com.trust.walletclone.fragment.WalletFragment
import com.trust.walletclone.util.Coin
import wallet.core.jni.CoinType
import wallet.core.jni.HDWallet

class MainActivity : AppCompatActivity() {
    init {
        System.loadLibrary("TrustWalletCore")
    }

    private lateinit var binding: ActivityMainBinding
    public lateinit var currentWallet: HDWallet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initData()
        initView()
    }

    fun initData() {
        val sharedPreference = getSharedPreferences("crypto_wallet", Context.MODE_PRIVATE)
        val seed = sharedPreference.getString("saved_wallet", "")
        var wallet: HDWallet = HDWallet(seed,"")
        val coinEth: CoinType = CoinType.ETHEREUM
        // Get the default address
        val addressEth = wallet.getAddressForCoin(coinEth)
        Log.e("address", addressEth)
        setWallet(wallet)
    }

    fun initView() {
        binding.navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        replaceFragments(WalletFragment())
    }

    public fun setWallet(wallet: HDWallet) {
        currentWallet = wallet
    }

    public fun getWallet(): HDWallet {
        return currentWallet
    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_wallet -> {
                    replaceFragments(WalletFragment())
                    return@OnNavigationItemSelectedListener true
                }
//            R.id.navigation_discover -> {
//                replaceFragments(DiscoverFragment())
//                return@OnNavigationItemSelectedListener true
//            }
                R.id.navigation_dapp -> {
                    replaceFragments(DappFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_setting -> {
                    replaceFragments(SettingFragment())
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    fun replaceFragments(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        fragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
            )
            .commit()
    }

    public fun openUrls(v: View) {
        val openURL = Intent(Intent.ACTION_VIEW)
        when (v.id) {
            R.id.btn_help -> openURL.data = Uri.parse("https://trustwallet.com")
            R.id.btn_twitter -> openURL.data = Uri.parse("https://trustwallet.com")
            R.id.btn_telegram -> openURL.data = Uri.parse("https://trustwallet.com")
            R.id.btn_facebook -> openURL.data = Uri.parse("https://trustwallet.com")
            R.id.btn_youtube -> openURL.data = Uri.parse("https://trustwallet.com")
            R.id.btn_about -> openURL.data = Uri.parse("https://trustwallet.com")
        }
        startActivity(openURL)
    }
}