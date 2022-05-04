package com.trust.walletclone.util

import android.content.Context
import android.util.Log
import com.google.gson.annotations.SerializedName
import okhttp3.OkHttpClient
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import java.net.URL
import java.net.URLEncoder
import okhttp3.ResponseBody
import java.lang.Exception


class Global {
    private val TAG: String = "Crypto Wallet"

    fun save(context: Context, value: String, type: String) {

        val sharedPreference =
            context.getSharedPreferences("crypto_wallet", Context.MODE_PRIVATE)
        sharedPreference.edit().putString("saved_wallet", value).commit()
        
    }

    interface ApiInterface {
        @Headers("Content-Type: application/json")
        @POST("api")
        fun saveSetting(@Body body: String?): Call<String?>?
    }
}