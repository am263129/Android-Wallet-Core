package com.trust.walletclone.activity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.Display
import android.view.WindowManager
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.protobuf.ByteString
import com.google.zxing.WriterException
import com.trust.walletclone.databinding.ActivityReceiveTokenBinding
import com.trust.walletclone.dialog.SetAmountDialog
import wallet.core.jni.CoinType
import java.math.BigInteger
import kotlin.experimental.and


class ReceiveTokenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReceiveTokenBinding

    var address :String = ""
    var symbol:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReceiveTokenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        address = intent.getStringExtra("address").toString()
        symbol = intent.getStringExtra("token").toString()
        initView()
    }

    fun initView(){
        binding.toolbar.btnBack.setOnClickListener {
            finish()
        }
        binding.toolbar.title.text = "Receive ${symbol}"
        binding.btnCopy.setOnClickListener {
            val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("text", address)
            clipboardManager.setPrimaryClip(clipData)
            Snackbar.make(binding.root, "Copied. ${address}", Snackbar.LENGTH_SHORT).show()
        }
        binding.btnShare.setOnClickListener {
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            val shareBody = "My Public Address to Receive ${symbol} ${address}"
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Share")
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
            startActivity(Intent.createChooser(sharingIntent, "Share via"))
        }
        binding.btnSetamount.setOnClickListener {
            val amountManage = SetAmountDialog()
            amountManage.onConfirm = { amount->
                generateQRcode(amount)
                amountManage.hideLoading()
            }

            amountManage.displayLoadingWithText(this, "Enter Amount", true)
        }
        generateQRcode(0f)
    }

    private fun generateQRcode(amount: Float) {
        val manager = getSystemService(WINDOW_SERVICE) as WindowManager?
        val display: Display = manager!!.defaultDisplay
        val point = Point()
        display.getSize(point)
        val width: Int = point.x
        val height: Int = point.y
        var dimen = if (width < height) width else height
        dimen = dimen / 2
        var coinType: CoinType
        if(symbol.toLowerCase() == "ethereum"){
            coinType = CoinType.ETHEREUM
        }
        else{
            coinType = CoinType.BINANCE
        }
        var target = "${symbol.toLowerCase()}:${address}"
        if (amount > 0){
            target = "${target}?amount:${amount}"
        }
        var qrgEncoder = QRGEncoder(target, null, QRGContents.Type.TEXT,dimen )
        try {
            var bitmap = qrgEncoder.bitmap
            binding.qrView.setImageBitmap(bitmap)
        } catch (e: WriterException) {
            Log.e("Tag", e.toString())
        }
        binding.qrString.text = address
    }

    private fun ByteArray.toHexString(withPrefix: Boolean = true): String {
        val stringBuilder = StringBuilder()
        if(withPrefix) {
            stringBuilder.append("0x")
        }
        for (element in this) {
            stringBuilder.append(String.format("%02x", element and 0xFF.toByte()))
        }
        return stringBuilder.toString()
    }

    private fun BigInteger.toByteString(): ByteString {
        return ByteString.copyFrom(this.toByteArray())
    }
}