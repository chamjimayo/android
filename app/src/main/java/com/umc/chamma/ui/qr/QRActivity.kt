package com.umc.chamma.ui.qr

import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import com.journeyapps.barcodescanner.*
import com.umc.chamma.config.BaseActivityVB
import com.umc.chamma.databinding.ActivityQrBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class QRActivity : BaseActivityVB<ActivityQrBinding>(ActivityQrBinding::inflate) {

    private lateinit var capture : CustomCaptureManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       // capture = CaptureManager(this,binding.decoratedBarcodeView)
        //capture.initializeFromIntent(intent,savedInstanceState)
       // capture.decode()
        initializeQrScanner(savedInstanceState)

    }
    private fun initializeQrScanner(savedInstanceState: Bundle?) {
        with(binding) {
            capture = CustomCaptureManager(this@QRActivity, decoratedBarcodeView)
            capture.initializeFromIntent(intent, savedInstanceState)
            capture.setShowMissingCameraPermissionDialog(false)
            capture.decode()
            capture.resultCallback {
                Log.d("Tester", "returnResult: $it")
                Toast.makeText(this@QRActivity,"returnResult: $it",Toast.LENGTH_LONG).show()
                CoroutineScope(Dispatchers.Main).launch {
                    capture.onPause()
                    capture.onDestroy()
                    delay(200)
                    initializeQrScanner(savedInstanceState)
                    capture.onResume()
                }
            }
        }
    }
    override fun onResume() {
        super.onResume()
        capture.onResume()
    }


    override fun onPause() {
        capture.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        capture.onDestroy()
        super.onDestroy()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        capture.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        capture.onSaveInstanceState(outState)
    }

    class CustomCaptureManager(
        private val activity: Activity,
        private val barcodeView: DecoratedBarcodeView
    ) : CaptureManager(activity, barcodeView) {

        private var callback: (BarcodeResult?) -> Unit = {}

        override fun returnResult(rawResult: BarcodeResult?) {
            callback(rawResult)
        }

        fun resultCallback(callback: (BarcodeResult?) -> Unit) {
            this.callback = callback
        }
    }
}