package com.android.chamma.ui.qr

import android.os.Bundle
import com.android.chamma.config.BaseActivityVB
import com.android.chamma.databinding.ActivityQrBinding
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

class QRActivity : BaseActivityVB<ActivityQrBinding>(ActivityQrBinding::inflate) {

    private lateinit var capture : CaptureManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        capture = CaptureManager(this,binding.decoratedBarcodeView)
        capture.initializeFromIntent(intent,savedInstanceState)
        capture.decode()


    }

    override fun onResume() {
        super.onResume()
        capture.onResume()
    }


    override fun onPause() {
        super.onPause()
        capture.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        capture.onDestroy()
    }

}