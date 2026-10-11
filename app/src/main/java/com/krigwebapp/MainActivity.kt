
package com.krigwebapp

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.view.View
import android.view.WindowInsets
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import android.view.WindowInsetsController

class MainActivity : Activity() {

    private var webView: WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterFullscreen()

        val browser = WebView(this)
        webView = browser

        browser.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            mediaPlaybackRequiresUserGesture = false
            javaScriptCanOpenWindowsAutomatically = true
            setSupportMultipleWindows(false)
            allowFileAccess = false
            allowContentAccess = true
        }

        CookieManager.getInstance().apply {
            setAcceptCookie(true)
            setAcceptThirdPartyCookies(browser, true)
        }

        browser.webViewClient = WebViewClient()
        browser.webChromeClient = WebChromeClient()
        browser.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        browser.keepScreenOn = true

        setContentView(browser)
        browser.loadUrl("https://comdh.krig.games/")
    }

        private fun enterFullscreen() {
        WindowCompat.setDecorFitsSystemWindows(window, false)

        WindowInsetsControllerCompat(window, window.decorView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        if (hasFocus) {
            enterFullscreen()
        }
    }

    @Suppress("DEPRECATION")
    override fun onBackPressed() {
        val browser = webView

        if (browser != null && browser.canGoBack()) {
            browser.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        webView?.destroy()
        webView = null
        super.onDestroy()
    }
}
