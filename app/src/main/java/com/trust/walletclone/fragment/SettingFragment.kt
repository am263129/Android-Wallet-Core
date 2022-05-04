package com.trust.walletclone.fragment

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trust.walletclone.activity.SecurityActivity
import com.trust.walletclone.activity.SplashActivity
import com.trust.walletclone.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {
    private lateinit var binding: FragmentSettingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingBinding.inflate(inflater)
        initView()
        // Inflate the layout for this fragment
        return binding.root
    }

    fun initView() {
        binding.toolbar.title.text = "Settings"
        binding.btnSecurity.setOnClickListener {
            var intent = Intent(context, SecurityActivity::class.java)
            startActivity(intent)
        }
        binding.btnRemove.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(requireContext())
            dialogBuilder.setMessage("Do you want to delete wallet ?")
                .setCancelable(false)
                .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id ->
                    val sharedPreference = requireActivity().getSharedPreferences(
                        "crypto_wallet",
                        Context.MODE_PRIVATE
                    )
                    sharedPreference.edit().putString("saved_wallet", "").apply()
                    val intent = Intent(requireActivity(), SplashActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()

                })
                // negative button text and action
                .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
                    dialog.cancel()
                })

            val alert = dialogBuilder.create()
            alert.setTitle("Delete Wallet")
            alert.show()
        }
    }
}