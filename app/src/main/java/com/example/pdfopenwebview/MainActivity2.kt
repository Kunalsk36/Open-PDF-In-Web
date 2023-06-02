package com.example.pdfopenwebview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import android.Manifest
import android.app.AlertDialog
import android.app.DownloadManager
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

class MainActivity2 : AppCompatActivity() {

    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private var isReadPermissionGranted = false
    private var isWritePermissionGranted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val link = intent.getStringExtra("pdf_url")

        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            isReadPermissionGranted = isGranted
            if (isReadPermissionGranted) {
                Log.d("Permission", "Read permission granted")
            } else {
                Log.d("Permission", "Read permission denied")
            }
            isWritePermissionGranted = isGranted
            if (isWritePermissionGranted) {
                Log.d("Permission", "Write permission granted")
            } else {
                Log.d("Permission", "Write permission denied")
            }
        }
        requestPermission()
        val wv = findViewById<WebView>(R.id.webview)
        wv.loadUrl("$link")
        wv.webViewClient = Client()
        val ws = wv.settings
        ws.javaScriptEnabled = true
        wv.settings.javaScriptCanOpenWindowsAutomatically = true
        wv.clearCache(true)
        wv.clearHistory()
        wv.setDownloadListener { url, _, _, _, _ ->
            val req = DownloadManager.Request(Uri.parse(url))
            req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            val dm = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
            dm.enqueue(req)
            Toast.makeText(this@MainActivity2, "Downloading....", Toast.LENGTH_SHORT).show()
        }
    }

    private fun requestPermission() {
        isReadPermissionGranted =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        if (!isReadPermissionGranted) {
            permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        isWritePermissionGranted =
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        if (!isWritePermissionGranted) {
            permissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    private inner class Client : WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
        }

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            view?.loadUrl(url!!)
            return true
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
        }

        override fun onReceivedError(
            webView: WebView?,
            errorCode: Int,
            description: String?,
            failingUrl: String?
        ) {
            try {
                webView?.stopLoading()
            } catch (e: Exception) {
            }
            if (webView?.canGoBack() == true) {
                webView.goBack()
            }
            webView?.loadUrl("about:blank")
            val alertDialog = AlertDialog.Builder(this@MainActivity2).create()
            alertDialog.setTitle("Error")
            alertDialog.setMessage("Check your internet connection and Try again.")
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Try Again") { dialog, which ->
                finish()
                startActivity(intent)
            }
            alertDialog.show()
            super.onReceivedError(webView, errorCode, description, failingUrl)
        }
    }
}