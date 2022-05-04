package com.trust.walletclone.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.google.android.material.snackbar.Snackbar
import com.trust.walletclone.R
import com.trust.walletclone.databinding.ActivityPasswordBinding

class PasswordActivity : AppCompatActivity(){
    private lateinit var binding: ActivityPasswordBinding
    private var passcode: String = ""
    private var matchPasscode: String = ""
    private var passcodeView: ArrayList<ImageView> = ArrayList()
    var passcodeSetting: Int = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        passcodeView.add(binding.pass1)
        passcodeView.add(binding.pass2)
        passcodeView.add(binding.pass3)
        passcodeView.add(binding.pass4)
        passcodeView.add(binding.pass5)
        passcodeView.add(binding.pass6)
        passcodeSetting = intent.getIntExtra("passcode",-1)
    }

    public fun clickNumber(v: View) {
        when (v.id) {
            R.id.btn_num_one -> setPasscode(1)
            R.id.btn_num_two -> setPasscode(2)
            R.id.btn_num_three -> setPasscode(3)
            R.id.btn_num_four -> setPasscode(4)
            R.id.btn_num_five -> setPasscode(5)
            R.id.btn_num_six -> setPasscode(6)
            R.id.btn_num_seven -> setPasscode(7)
            R.id.btn_num_eight -> setPasscode(8)
            R.id.btn_num_nine -> setPasscode(9)
            R.id.btn_num_zero -> setPasscode(0)
            R.id.btn_backspace -> setPasscode(-1)
        }
    }

    private fun setPasscode(n: Int) {
        if (n < 0){
            passcode = passcode.dropLast(1)
        }
        else {
            passcode += n
        }
        updateUi()
        if (passcode.length == 6) {
            //from security activity. request disable passcode
            if (passcodeSetting == 1){
                //check passcode.
                finish()
            }

            if(matchPasscode.isEmpty()){
                matchPasscode = passcode
                passcode = ""
                binding.labelNewpasscode.text = getString(R.string.lbl_re_enter_passcode)
                updateUi()
            }
            else{
                if(matchPasscode == passcode){
                    //set new passcode from security activity
                    if(passcodeSetting == 2){
                        //save passcode
                        finish()
                    }
                    else{
                        var intent = Intent(this, PhraseActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
                else{
                    passcode = ""
                    Snackbar.make(binding.root, "Passcode does not match.", Snackbar.LENGTH_SHORT).show()
                    updateUi()
                }
            }

        }
    }

    private fun updateUi(){
        for(view in passcodeView){
            view.setBackgroundResource(R.drawable.ic_circle)
        }
        var i = 0
        while (i<passcode.length){
            passcodeView.get(i).setBackgroundResource(R.drawable.ic_circle_fill)
            i++
        }
    }
}