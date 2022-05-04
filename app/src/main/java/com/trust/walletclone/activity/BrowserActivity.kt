package com.trust.walletclone.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.trust.walletclone.R
import com.trust.walletclone.databinding.ActivityBrowserBinding
import com.trust.walletclone.util.Progress


class BrowserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBrowserBinding
    private var progress: Progress? = null
    private var isLoaded: Boolean = false
    private var doubleBackToExitPressedOnce = false
    private var webURL = ""

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browser)
        val inflater = LayoutInflater.from(this)
        binding = ActivityBrowserBinding.inflate(inflater)
        binding.browserView.settings.javaScriptEnabled = true
        binding.browserView.settings.loadWithOverviewMode = true
        binding.browserView.settings.useWideViewPort = true
//        if (!isOnline()) {
//            showToast(getString(R.string.no_internet))
//            binding.info.text = getString(R.string.no_internet)
//            showNoNetSnackBar()
//            return
//        }
        webURL = intent.getStringExtra("URL").toString()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.browserView.settings.safeBrowsingEnabled = false
        }

        binding.browserView.settings.cacheMode = WebSettings.LOAD_NO_CACHE
        binding.browserView.settings.databaseEnabled = false
        binding.browserView.settings.domStorageEnabled = false
        binding.browserView.settings.setGeolocationEnabled(false)
//        settings.setUserAgentString("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36");

        //        settings.setUserAgentString("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36");
        binding.browserView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                Log.e("Progress: ", "Progreess: $newProgress")
                super.onProgressChanged(view, newProgress)
            }
        }
        binding.btnBack.setOnClickListener {
            finish()
        }
        loadWebView()
    }


    override fun onResume() {
//        if (isOnline() && !isLoaded) loadWebView()
//        loadWebView()
        super.onResume()
    }

    private fun loadWebView() {
        Log.e("load?",webURL)

        binding.browserView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url != null) {
                    view?.loadUrl(url)
                }
                return true
            }
        }
        binding.browserView.loadUrl("https://google.com")
//
//        binding.info.text = ""
//        binding.browserView.loadUrl(webURL)
//        binding.browserView.webViewClient = object : WebViewClient() {
//            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
//                val url = request?.url.toString()
//                view?.loadUrl(url)
//                return super.shouldOverrideUrlLoading(view, request)
//            }
//
//            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
//                setProgressDialogVisibility(true)
//                super.onPageStarted(view, url, favicon)
//            }
//
//            override fun onPageFinished(view: WebView?, url: String?) {
//                isLoaded = true
//                setProgressDialogVisibility(false)
//                super.onPageFinished(view, url)
//            }
//
//            override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
//                isLoaded = false
//                val errorMessage = "Got Error! $error"
//                showToast(errorMessage)
//                binding.info.text = errorMessage
//                setProgressDialogVisibility(false)
//                super.onReceivedError(view, request, error)
//            }
//        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (binding.browserView.canGoBack()) {
                    binding.browserView.goBack()
                } else {
                    showToastToExit()
                }
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun showToastToExit() {
        when {
            doubleBackToExitPressedOnce -> {
                onBackPressed()
            }
            else -> {
                doubleBackToExitPressedOnce = true
                showToast(getString(R.string.back_again_to_exit))
                Handler(Looper.myLooper()!!).postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
            }
        }
    }

    private fun setProgressDialogVisibility(visible: Boolean) {
        if (visible) progress = Progress(this, R.string.please_wait, cancelable = true)
        progress?.apply { if (visible) show() else dismiss() }
    }

    private fun isOnline(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showNoNetSnackBar() {
        val snack = Snackbar.make(binding.rootView, getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
        snack.setAction(getString(R.string.settings)) {
            startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
        }
        snack.show()
    }

    override fun onBackPressed() {
        // if your webview can go back it will go back
        if (binding.browserView.canGoBack())
            binding.browserView.goBack()
        // if your webview cannot go back
        // it will exit the application
        else
            super.onBackPressed()
    }
}