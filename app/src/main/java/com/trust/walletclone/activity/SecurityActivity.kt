package com.trust.walletclone.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.trust.walletclone.R
import com.trust.walletclone.databinding.ActivityMainBinding
import com.trust.walletclone.databinding.ActivitySecurityBinding

class SecurityActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySecurityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecurityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    fun initView() {
        binding.btnTogglePasscode.setOnCheckedChangeListener { compoundButton, b ->
            var intent = Intent(this, PasswordActivity::class.java)
            intent.putExtra("passcode",if (b) 1 else 2)
            startActivity(intent)
        }
        binding.toolbar.btnBack.setOnClickListener {
            finish()
        }
    }
}