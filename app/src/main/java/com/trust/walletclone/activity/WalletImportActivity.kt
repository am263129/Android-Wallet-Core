package com.trust.walletclone.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.trust.walletclone.R
import com.trust.walletclone.databinding.ActivityMainBinding
import com.trust.walletclone.databinding.ActivityWalletImportBinding
import com.trust.walletclone.fragment.WalletImportSelectFragment

class WalletImportActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWalletImportBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWalletImportBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    fun initView() {
        replaceFragments(WalletImportSelectFragment())
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
}