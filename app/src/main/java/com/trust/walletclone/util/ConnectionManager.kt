package com.trust.walletclone.util

import android.util.Log
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.methods.response.Web3ClientVersion
import org.web3j.protocol.http.HttpService
import org.web3j.tx.FastRawTransactionManager

class ConnectionManager {
    public fun connect(coin: Coin): Web3j? {
        var web3:Web3j
        return try {
            web3 = Web3j.build(HttpService(coin.url))
            val clientVersion: Web3ClientVersion = web3.web3ClientVersion().sendAsync().get()
            if (!clientVersion.hasError()) {
                Log.e("Connecting", "Success")
                web3
            } else {
                Log.e( "Connecting error", clientVersion.error.toString())
                null
            }
        } catch (e: Exception) {
            Log.e( "Connecting Failed", e.message.toString())
            null
        }
    }
}