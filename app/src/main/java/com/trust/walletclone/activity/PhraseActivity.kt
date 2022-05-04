package com.trust.walletclone.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.trust.walletclone.R
import com.trust.walletclone.dialog.LoadingDialog
import com.trust.walletclone.fragment.BackupPhraseFragment
import com.trust.walletclone.fragment.WalletFragment


class PhraseActivity : AppCompatActivity() {
    init {
        System.loadLibrary("TrustWalletCore")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phrase)
        val fragment = BackupPhraseFragment()
        replaceFragments(fragment)
    }

    private fun showFragment(fragment: Fragment){
        val fram = supportFragmentManager.beginTransaction()
        fram.replace(R.id.fragment_container,fragment)
        fram.commit()
    }

    fun replaceFragments(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
            .setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out)
            .addToBackStack(null)
            .commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}