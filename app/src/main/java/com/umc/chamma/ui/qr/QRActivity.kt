package com.umc.chamma.ui.qr

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import com.journeyapps.barcodescanner.*
import com.umc.chamma.R
import com.umc.chamma.config.App
import com.umc.chamma.config.BaseActivityVB
import com.umc.chamma.databinding.ActivityQrBinding
import com.umc.chamma.databinding.DialogQrResult2Binding
import com.umc.chamma.databinding.DialogQrResultBinding
import com.umc.chamma.ui.home.restroomInfo.RestroomInfoActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class QRActivity : BaseActivityVB<ActivityQrBinding>(ActivityQrBinding::inflate) {

    private lateinit var capture : CustomCaptureManager
    private var Id by Delegates.notNull<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       // capture = CaptureManager(this,binding.decoratedBarcodeView)
        //capture.initializeFromIntent(intent,savedInstanceState)
       // capture.decode()
        initializeQrScanner(savedInstanceState)
        Id= intent.getIntExtra("ID",0)
        Log.d("qr연결결과 ",Id.toString())

    }
    private fun initializeQrScanner(savedInstanceState: Bundle?) {
        with(binding) {
            capture = CustomCaptureManager(this@QRActivity, decoratedBarcodeView)
            capture.initializeFromIntent(intent, savedInstanceState)
            capture.setShowMissingCameraPermissionDialog(false)
            capture.decode()
            capture.resultCallback {
                Log.d("Tester", "returnResult: $it")
                val data = it?.text?.split('/')?.last()
                if(data?.toInt()==Id){
                    /*
                      val dialog = BottomSheetDialog(context)
        val binding = FragmentBtmshtdialogSortListBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(binding.root)

        dialog.show()
                     */
                    val myDialogBinding = DialogQrResult2Binding.inflate(LayoutInflater.from(this@QRActivity))
                    val build = AlertDialog.Builder(this@QRActivity).setView(myDialogBinding.root)
                    val dialog = build.create()
                    dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                    dialog.show()


                    myDialogBinding.btnPostEditBackCancel.setOnClickListener {
                        showCustomToast("참을래요 완료")
                        dialog.dismiss()
                        //finish()
                    }
                    myDialogBinding.btnPostEditBackOk.setOnClickListener{
                        showCustomToast("이용할래요 완료")
                        dialog.dismiss()
                        finish()
                        val intent= Intent(App.context(), QrPointResultActivity::class.java)
                        startActivity(intent)
                    }
                }
                else{
                    val myDialogBinding = DialogQrResultBinding.inflate(LayoutInflater.from(this@QRActivity))
                    val build = AlertDialog.Builder(this@QRActivity).setView(myDialogBinding.root)
                    val dialog = build.create()
                    dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                    dialog.show()

                    myDialogBinding.btnPostEditBackCancel.setOnClickListener {
                        showCustomToast("닫기 완료")
                        dialog.dismiss()
                        // activity 종료하기
                        finish()
                    }

                    myDialogBinding.btnPostEditBackOk.setOnClickListener {
                        showCustomToast("재시도 완료")
                        dialog.dismiss()
                    }
                }

                Toast.makeText(this@QRActivity,"returnResult: $data",Toast.LENGTH_LONG).show()
                CoroutineScope(Dispatchers.Main).launch {
                    capture.onPause()
                    capture.onDestroy()
                    delay(500)
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