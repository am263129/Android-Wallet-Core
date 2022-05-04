package com.trust.walletclone.fragment

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.trust.walletclone.R
import com.trust.walletclone.activity.MainActivity
import com.trust.walletclone.activity.ReceiveTokenActivity
import com.trust.walletclone.databinding.FragmentTokenBinding
import com.trust.walletclone.dialog.LoadingDialog
import com.trust.walletclone.dialog.SendTokenDialog
import com.trust.walletclone.dialog.SwapDialog
import com.trust.walletclone.util.Coin
import com.trust.walletclone.util.CoinManager
import com.trust.walletclone.util.ConnectionManager
import org.web3j.crypto.Credentials
import org.web3j.crypto.RawTransaction
import org.web3j.crypto.TransactionEncoder
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.core.methods.response.EthGasPrice
import org.web3j.protocol.core.methods.response.EthSendTransaction
import org.web3j.utils.Convert
import org.web3j.utils.Numeric
import wallet.core.jni.CoinType
import wallet.core.jni.HDWallet
import java.io.IOException
import java.io.InputStream
import java.math.BigDecimal
import java.math.BigInteger
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.pow


class TokenFragment(private var coin: Coin) : Fragment() {

    private lateinit var binding: FragmentTokenBinding
    var coinList: ArrayList<Coin> = ArrayList()
    private lateinit var web3: Web3j
    private lateinit var sendDialog: SendTokenDialog
    private lateinit var wallet: HDWallet
    private lateinit var coinType: CoinType
    private lateinit var currentBalance: BigDecimal
    private lateinit var gasPrice: BigInteger
    private var max: String = "0"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTokenBinding.inflate(inflater)
        wallet = (requireActivity() as MainActivity).getWallet()
        coinType = if (coin.name.lowercase(Locale.getDefault()) == "ethereum") {
            CoinType.ETHEREUM
        } else {
            CoinType.BINANCE
        }

        initView()
        return binding.root
    }


    fun initView() {
        connectWeb3()

        binding.refreshContainer.setOnRefreshListener {
            binding.refreshContainer.postDelayed({
                connectWeb3()
                binding.refreshContainer.isRefreshing = false
            }, 2000)
        }

        binding.toolbar.title.text = "${coin.name} (${coin.symbol})"
        binding.coinType.text = coin.type
        binding.coinRate.text = "$${coin.rate}"
        binding.coinPercent.text = "${coin.percent}%"
        binding.coinBalance.text = coin.balance.toString()
        binding.coinSymbol.text = coin.symbol
        binding.coinPercent.setTextColor(
            ResourcesCompat.getColor(
                resources,
                if (coin.percent > 0) R.color.blue else R.color.red,
                null
            )
        )
        var imageStream: InputStream? = null
        try {
            imageStream = requireContext().assets.open("coin/" + coin.icon + ".webp")
            val drawable = Drawable.createFromStream(imageStream, null)
            binding.coinIcon.setImageDrawable(drawable)
        } catch (ex: IOException) {
            Log.e("error", ex.toString())
            return
        }

        binding.toolbar.btnBack.setOnClickListener {
            val fm: FragmentManager = requireActivity().supportFragmentManager
            fm.popBackStackImmediate()
        }
        binding.btnSend.setOnClickListener {
            sendDialog = SendTokenDialog(coin, max)
            sendDialog.onSend = { address, amount, memo ->
                sendToken(address, amount, memo)
            }
            sendDialog.show(childFragmentManager, "SendingDialog")
        }
        binding.btnReceive.setOnClickListener {
            var intent = Intent(activity, ReceiveTokenActivity::class.java)
            intent.putExtra("token", coin.symbol)
            intent.putExtra("address", wallet.getAddressForCoin(CoinType.ETHEREUM))
            startActivity(intent)
        }
        binding.btnSwap.setOnClickListener {
            SwapDialog().show(childFragmentManager, "SwapDialog")
        }
        binding.btnStake.setOnClickListener {
            (activity as MainActivity).replaceFragments(TokenStakeStatusFragment(coin))
        }
    }

    private fun connectWeb3() {
        binding.refreshContainer.isRefreshing = true
        web3 = ConnectionManager().connect(coin)!!
        binding.refreshContainer.isRefreshing = false
        getBalance()
    }

    private fun getBalance() {
        binding.refreshContainer.isRefreshing = true
        var balance: BigInteger? = BigInteger.valueOf(0)
        val thread = Thread {
            try {
                balance =
                    CoinManager().getBalance(web3, wallet.getAddressForCoin(CoinType.ETHEREUM))
                if (balance != null) {
                    val floatBalance = Convert.fromWei(balance.toString(), Convert.Unit.ETHER)
                    gasPrice = web3.ethGasPrice().send().gasPrice
                    max = Convert.fromWei(
                        balance!!.minus(
                            BigInteger("21000").multiply(
                                gasPrice
                            )
                        ).toString(),
                        Convert.Unit.ETHER
                    ).toString()
                    currentBalance = floatBalance
                    requireActivity().runOnUiThread {
                        binding.coinBalance.text = "%,.4f".format(floatBalance)
                        binding.refreshContainer.isRefreshing = false
                    }
                }
            }
            catch (e:Exception){
                Log.e("Error", e.message.toString())
            }
        }
        thread.start()
    }

    fun sendToken(address: String, amount: Float, memo: String) {
        sendDialog.dismiss()
        val loading: LoadingDialog = LoadingDialog()
        loading.displayLoadingWithText(requireActivity(), "sending token..", false)
        val thread = Thread {
            try {
                var credentials: Credentials =
                    Credentials.create(wallet.getKeyForCoin(CoinType.ETHEREUM).data().toHexString())
                val ethGasPrice: EthGasPrice = web3.ethGasPrice().send()
                val nonce: BigInteger =
                    web3.ethGetTransactionCount(
                        wallet.getAddressForCoin(CoinType.ETHEREUM),
                        DefaultBlockParameterName.LATEST
                    ).send()
                        .transactionCount
                val gasLimit = BigInteger("21000")
                val rawTransaction: RawTransaction = RawTransaction.createTransaction(
                    nonce,
                    gasPrice,
                    gasLimit,
                    address,
                    BigInteger.valueOf(
                        Convert.toWei(amount.toString(), Convert.Unit.ETHER).toLong()
                    ),
                    ""
                )
                val signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials)
                val hexValue = Numeric.toHexString(signedMessage)
                val transactionResponse: EthSendTransaction = web3.ethSendRawTransaction(hexValue)
                    .sendAsync().get(30000, TimeUnit.SECONDS)
                if (transactionResponse.getError() != null) {
                    Log.e("error", transactionResponse.error.message.toString())
                    requireActivity().runOnUiThread {
                        Toast.makeText(
                            requireActivity(),
                            "Error ".plus(transactionResponse.error.message.toString()),
                            Toast.LENGTH_LONG
                        ).show()
                        loading.hideLoading()
                    }
                } else {
                    Log.e("result", "Transaction complete: " + transactionResponse.transactionHash)
                    requireActivity().runOnUiThread {
                        Toast.makeText(
                            requireActivity(),
                            "Success",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    loading.hideLoading()
                    getBalance()
                }
            } catch (e: Exception) {
                Log.e("error", e.message.toString())
                requireActivity().runOnUiThread {
                    Toast.makeText(
                        requireActivity(),
                        "Error ".plus(e.message.toString()),
                        Toast.LENGTH_LONG
                    ).show()
                    loading.hideLoading()
                    getBalance()
                }
            }
        }
        thread.start()
    }


    fun ByteArray.toHexString() =
        asUByteArray().joinToString("") { it.toString(16).padStart(2, '0') }
}