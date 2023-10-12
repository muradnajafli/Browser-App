package com.example.browserapp

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.browserapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupWebView()
        setupToolbar()
        setupSearchButton()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        binding.webView.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            webChromeClient = object : WebChromeClient() {
                override fun onReceivedTitle(view: WebView?, title: String?) {
                    if (title.isNullOrEmpty() || title == "about:blank") {
                        binding.toolbar.title = resources.getString(R.string.app_name)
                    } else {
                        binding.toolbar.title = title
                    }
                }
            }
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun setupSearchButton() {
        binding.searchButton.setOnClickListener {
            val inputText = binding.textInputEditText.text.toString()
            binding.webView.loadUrl(inputText)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        updateMenu(newConfig.orientation)
        binding.searchButton.setBackgroundColor(Color.parseColor(COLOR_BLUE))
    }

    private fun updateMenu(orientation: Int) {
        val menuResId = if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            R.menu.menu_landscape
        } else {
            R.menu.menu_portrait
        }
        binding.toolbar.menu.clear()
        menuInflater.inflate(menuResId, binding.toolbar.menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.options_green -> changeButtonColor(Color.parseColor(COLOR_GREEN))
            R.id.options_blue -> changeButtonColor(Color.parseColor(COLOR_BLUE))
            R.id.options_violet -> changeButtonColor(Color.parseColor(COLOR_VIOLET))
            R.id.options_brown -> changeButtonColor(Color.parseColor(COLOR_BROWN))
            R.id.options_yellow -> changeButtonColor(Color.parseColor(COLOR_YELLOW))
            R.id.options_orange -> changeButtonColor(Color.parseColor(COLOR_ORANGE))
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_portrait, menu)
        return true
    }

    private fun changeButtonColor(color: Int) {
        binding.searchButton.backgroundTintList = ColorStateList.valueOf(color)
    }
}

