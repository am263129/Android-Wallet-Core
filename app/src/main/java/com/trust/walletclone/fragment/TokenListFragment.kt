package com.trust.walletclone.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kenai.jffi.Main
import com.trust.walletclone.activity.MainActivity
import com.trust.walletclone.adapter.CoinAdapter
import com.trust.walletclone.databinding.FragmentTokenListBinding
import com.trust.walletclone.util.Coin
import com.trust.walletclone.util.CoinManager
import com.trust.walletclone.util.ConnectionManager
import com.trust.walletclone.util.DBHelper
import org.web3j.protocol.Web3j
import org.web3j.utils.Convert
import wallet.core.jni.CoinType
import kotlin.math.pow


class TokenListFragment : Fragment() {

    private lateinit var binding: FragmentTokenListBinding
    var coinList: ArrayList<Coin> = ArrayList()
    lateinit var coinAdapter: CoinAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTokenListBinding.inflate(inflater)
        initView()
        return binding.root
    }


    fun initView() {
        coinList = DBHelper(requireContext()).readAllCoins()
        binding.swipeRefreshLayout.isRefreshing = true
        val thread = Thread {
            try {
                for (coin in coinList) {
                    val web3: Web3j? = ConnectionManager().connect(coin)
                    val address: String = (requireActivity() as MainActivity).getWallet()
                        .getAddressForCoin(CoinType.ETHEREUM)
                    val balance = CoinManager().getBalance(web3!!, address)
                    if (balance != null) {
                        val floatBalance = Convert.fromWei(balance.toString(), Convert.Unit.ETHER)
                        coin.balance = floatBalance.toFloat()
                    }
                }
                requireActivity().runOnUiThread {
                    binding.swipeRefreshLayout.isRefreshing = false
                    coinAdapter.notifyDataSetChanged()
                }
            } catch (e: Exception) {
                Log.e("error", e.message.toString())
            }
        }
        thread.start()

        binding.listTokens.layoutManager = LinearLayoutManager(context)
        coinAdapter = CoinAdapter(coinList, context)
        coinAdapter.onItemClick = { coin ->
            val fragment = TokenFragment(coin)
            (activity as MainActivity).replaceFragments(fragment)
        }
        binding.listTokens.adapter = coinAdapter
    }
}