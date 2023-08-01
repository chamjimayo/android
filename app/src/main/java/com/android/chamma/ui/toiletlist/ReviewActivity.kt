package com.android.chamma.ui.toiletlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CalendarView
import android.widget.ImageButton
import androidx.activity.result.contract.ActivityResultContracts
import com.android.chamma.R
import com.android.chamma.config.BaseActivityVB
import com.android.chamma.databinding.ActivityReviewBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class ReviewActivity : BaseActivityVB<ActivityReviewBinding>(ActivityReviewBinding::inflate) {
    private lateinit var bottomSheet: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.optionBtn.setOnClickListener{
            bottomSheet = layoutInflater.inflate(R.layout.fragment_btmshtdialog_sort_list, null)
            val bottomSheetDialog = BottomSheetDialog(this)
            val btnClose = bottomSheet.findViewById<Button>(R.id.btmshtBtnClose)


            // calendar bottomsheetdialog 설정
            bottomSheetDialog.setContentView(bottomSheet)
            bottomSheetDialog.show()

            btnClose.setOnClickListener {
                bottomSheetDialog.dismiss()
            }


        }
    }
}