package com.trust.walletclone.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import cash.z.ecc.android.bip39.Mnemonics
import com.trust.walletclone.R
import com.trust.walletclone.activity.MainActivity
import com.trust.walletclone.adapter.SeedAdapter
import com.trust.walletclone.adapter.SeedVerifyAdapter
import com.trust.walletclone.databinding.FragmentVerifyPhraseBinding
import com.trust.walletclone.dialog.LoadingDialog
import com.trust.walletclone.util.Global
import wallet.core.jni.CoinType
import wallet.core.jni.HDWallet
import java.util.*

class VerifyPhraseFragment(var mnemonicCode: Mnemonics.MnemonicCode) : Fragment() {

    private lateinit var binding: FragmentVerifyPhraseBinding
    private val seedPhrase =
        ""
    private val passphrase = ""
    var seedArray: ArrayList<String> = ArrayList()
    var seedVerifyArray: ArrayList<String> = ArrayList()
    var checkArray: ArrayList<String> = ArrayList()
    lateinit var seedAdapter: SeedAdapter
    lateinit var seedVerifyAdapter: SeedVerifyAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVerifyPhraseBinding.inflate(inflater)
        initView()
        // Inflate the layout for this fragment
        return binding.root
    }


    fun initView() {

        var seedJoiner: StringJoiner = StringJoiner(" ")
        for (word in mnemonicCode) {
            seedJoiner.add(word)
            seedVerifyArray.add(word)
            checkArray.add(word)
        }
        seedVerifyArray.shuffle()
        binding.seedContainer.layoutManager = GridLayoutManager(context, 3)
        binding.seedVerifyContainer.layoutManager = GridLayoutManager(context, 4)
        seedAdapter = SeedAdapter(seedArray)
        seedAdapter.onItemClick = { seed ->
            seedArray.remove(seed.toString())
            seedVerifyArray.add(seed)
            verify()
        }
        seedVerifyAdapter = SeedVerifyAdapter(seedVerifyArray)
        seedVerifyAdapter.onItemClick = { vseed ->
            seedArray.add(vseed.toString())
            seedVerifyArray.remove(vseed)
            verify()
        }
        binding.seedContainer.adapter = seedAdapter
        binding.seedVerifyContainer.adapter = seedVerifyAdapter
        binding.btnDone.isEnabled = false
        binding.btnDone.setBackgroundColor(resources.getColor(R.color.gray800))
        binding.btnDone.setOnClickListener {
            val loding = LoadingDialog()
            loding.displayLoadingWithText(context, "Creating wallet...", false)
            val wallet = HDWallet(seedJoiner.toString(), passphrase)
            val coinEth: CoinType = CoinType.ETHEREUM
            val addressEth = wallet.getAddressForCoin(coinEth)
            Global().save(requireContext(),seedJoiner.toString(),"create")
            Handler().postDelayed(Runnable {
                loding.hideLoading()
                var intent = Intent(context, MainActivity::class.java)
                startActivity(intent)
            }, 3000)
        }
    }

    fun verify(){
        seedAdapter.notifyDataSetChanged()
        seedVerifyAdapter.notifyDataSetChanged()
        if(seedArray.size == 12 ){
            if(compareList(seedArray, checkArray)) {
                binding.btnDone.setBackgroundColor(resources.getColor(R.color.black))
                binding.btnDone.isEnabled = true
            }
            else{
                binding.lblWrong.visibility = View.VISIBLE
            }
        }
        else{
            binding.lblWrong.visibility = View.GONE
        }
    }

    fun compareList(ls1: ArrayList<String>, ls2: ArrayList<String>): Boolean {
        return ls1.toString().contentEquals(ls2.toString())
    }
}