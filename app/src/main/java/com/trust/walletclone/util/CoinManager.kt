package com.trust.walletclone.util

import android.util.Log
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.core.methods.response.EthGetBalance
import org.web3j.utils.Convert
import wallet.core.jni.CoinType
import java.io.IOException
import java.math.BigInteger
import kotlin.math.pow

class CoinManager {
    public fun getBalance(web3: Web3j, address:String): BigInteger? {
        var balance: BigInteger? = null
        try {
            try {
                val ethGetBalance: EthGetBalance =
                    web3.ethGetBalance(
                        address,
                        DefaultBlockParameterName.PENDING
                    ).send()
                balance = ethGetBalance.balance
                Log.e("balance", ethGetBalance.balance.toString())
            } catch (e: IOException) {
                Log.e("Error", e.message.toString())
                e.printStackTrace()
            }

            val floatBalance = Convert.fromWei(balance.toString(), Convert.Unit.ETHER)
            Log.e("balance", "%,.4f".format(floatBalance))
            return balance
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            Log.e("error !", e.message.toString())
            return null
        }
    }
}