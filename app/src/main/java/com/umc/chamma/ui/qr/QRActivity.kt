package com.umc.chamma.ui.qr

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import com.journeyapps.barcodescanner.*
import com.umc.chamma.config.App
import com.umc.chamma.config.BaseActivityVB
import com.umc.chamma.databinding.ActivityQrBinding
import com.umc.chamma.databinding.DialogQrResult2Binding
import com.umc.chamma.databinding.DialogQrResultBinding
import com.umc.chamma.ui.main.MainActivity
import com.umc.chamma.ui.mypage.chargepoint.ChargePointActivity
import com.umc.chamma.ui.mypage.chargepoint.model.UserinfoData
import com.umc.chamma.ui.qr.model.DeductPointResponse
import com.umc.chamma.ui.qr.model.UseRestroomResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class QRActivity : BaseActivityVB<ActivityQrBinding>(ActivityQrBinding::inflate)
    ,QrActivityInterface{

    val qrActivityInterface=this
    private lateinit var capture : CustomCaptureManager
    private var id :Int=0
    private var price :Int=0
    private var point = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        QrService(this).getUserInfo()
        // capture = CaptureManager(this,binding.decoratedBarcodeView)
        //capture.initializeFromIntent(intent,savedInstanceState)
        // capture.decode()
        id= intent.getIntExtra("ID",0)
        price=intent.getIntExtra("Price",0)
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
                val data = it?.text?.split('/')?.last()
                if(data?.toInt()==id){
                    val dialogBinding = DialogQrResult2Binding.inflate(LayoutInflater.from(this@QRActivity))
                    val build = AlertDialog.Builder(this@QRActivity).setView(dialogBinding.root)
                    val dialog = build.create()
                    dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    dialog.window?.setGravity(Gravity.BOTTOM);
                    dialog.show()

                    this@QRActivity.dialogResize(dialog, 0.7f, 0.6f)

                    if(price<=point) {
                        dialogBinding.tissueTv.text=price.toString()+"p"
                        dialogBinding.contentTv.text=price.toString()+"포인트가 차감됩니다\n  결제를 하실건가요?"
                        dialogBinding.btnPostEditBackCancel.setOnClickListener {
                            //showCustomToast("참을래요 완료")
                            dialog.dismiss()
                            val intent = Intent(App.context(), MainActivity::class.java)
                            startActivity(intent)
                            //finish()
                        }
                        dialogBinding.btnPostEditBackOk.setOnClickListener {
                            QrService(qrActivityInterface).tryToUseRestroom(id)

                            QrService(qrActivityInterface).tryToDeductPoint(price)

                            //showCustomToast("이용할래요 완료")
                            dialog.dismiss()
                            finish()
                            val intent = Intent(App.context(), QrPointResultActivity::class.java)
                            startActivity(intent)
                        }
                    }
                    else{
                        dialogBinding.tissueTv.text=point.toString()+"p 보유"
                        dialogBinding.contentTv.text="    앗,잠시만요!\n포인트가 부족해요."
                        dialogBinding.btnPostEditBackCancel.text="닫기"
                        dialogBinding.btnPostEditBackOk.text="충전하기"

                        dialogBinding.btnPostEditBackCancel.setOnClickListener {
                            dialog.dismiss()
                            val intent = Intent(App.context(), MainActivity::class.java)
                            startActivity(intent)
                        }
                        dialogBinding.btnPostEditBackOk.setOnClickListener {
                            val intent = Intent(App.context(), ChargePointActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }
                else{
                    val myDialogBinding = DialogQrResultBinding.inflate(LayoutInflater.from(this@QRActivity))
                    val build = AlertDialog.Builder(this@QRActivity).setView(myDialogBinding.root)
                    val dialog = build.create()
                    dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    dialog.window?.setGravity(Gravity.BOTTOM);
                    dialog.show()
                    this@QRActivity.dialogResize(dialog, 0.7f, 0.6f)


                    myDialogBinding.btnPostEditBackCancel.setOnClickListener {
                        //showCustomToast("닫기 완료")
                        dialog.dismiss()
                        // activity 종료하기
                        finish()
                    }

                    myDialogBinding.btnPostEditBackOk.setOnClickListener {
                        //showCustomToast("재시도 완료")
                        dialog.dismiss()
                    }
                }

                //Toast.makeText(this@QRActivity,"returnResult: $data",Toast.LENGTH_LONG).show()
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

    override fun onTryToUseRestroomSuccess(response: UseRestroomResponse) {
        Log.d("qr연결결과1 ",response.toString())
    }

    override fun onTryToUseRestroomFailure(message: String) {
        Log.d("qr연결결과1 ",message)
    }

    override fun onTryToDeductPointSuccess(response: DeductPointResponse) {
        Log.d("qr연결결과2 ",response.toString())
    }

    override fun onTryToDeductPointFailure(message: String) {
        Log.d("qr연결결과2 ",message)
    }

    override fun onGetUserInfoSuccess(data: UserinfoData) {
        point=data.point
        Log.d("포인트 ","api 성공")
    }

    override fun onGetUserInfoFailure(message: String) {
        TODO("Not yet implemented")
    }


    private fun Context.dialogResize(dialog: Dialog, width: Float, height: Float){
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        if (Build.VERSION.SDK_INT < 30){
            val display = windowManager.defaultDisplay
            val size = Point()

            display.getSize(size)

            val window = dialog.window

            val x = (size.x * width).toInt()
            val y = (size.y * height).toInt()

            window?.setLayout(x, y)

        }else{
            val rect = windowManager.currentWindowMetrics.bounds

            val window = dialog.window
            val x = (rect.width() * width).toInt()
            val y = (rect.height() * height).toInt()

            window?.setLayout(x, y)
        }

    }
}