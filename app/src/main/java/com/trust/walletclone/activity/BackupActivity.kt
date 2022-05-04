package com.trust.walletclone.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.trust.walletclone.R
import com.trust.walletclone.databinding.ActivityBackupBinding

class BackupActivity : AppCompatActivity(){
    private lateinit var binding: ActivityBackupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_backup)
    }


}