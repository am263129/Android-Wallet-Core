package com.trust.walletclone.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.kenai.jffi.Main
import com.trust.walletclone.R
import java.io.*
import java.nio.file.Files
import java.nio.file.Paths

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initData()


    }

    fun initData() {
        val sharedPreference = getSharedPreferences("crypto_wallet", Context.MODE_PRIVATE)
        //check if database file is copied.
        if (!sharedPreference.getBoolean("db_ready", false)) {
            if (copyDataBase())
                sharedPreference.edit().putBoolean("db_ready", true).commit()
        }
        Handler().postDelayed({
            val savedWallet = sharedPreference.getString("saved_wallet", "")
            var intent:Intent = if (savedWallet != "" ) {
                Intent(this, MainActivity::class.java)
            } else{
                Intent(this, WalletCreateActivity::class.java)
            }
            startActivity(intent)
        }, 2000)

    }

    @Throws(IOException::class)
    private fun copyDataBase(): Boolean {

        val path = getExternalFilesDir(null)
        val folder = File(path, "coin")
        val isNewFileCreated :Boolean = folder.createNewFile()
        if(isNewFileCreated){
            println("$folder is created successfully.")
        } else{
            println("$folder already exists.")
        }
        val myOutput = FileOutputStream(folder)
        val myInput = this.assets.open("coin.db")

        val buffer = ByteArray(1024)
        var length: Int = myInput.read(buffer)
        while ((length) > 0) {
            myOutput.write(buffer, 0, length)
            length = myInput.read(buffer)
        }
        myInput.close()
        myOutput.flush()
        myOutput.close()
        return true
    }
}
