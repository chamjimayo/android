package com.umc.chamma.ui.home


import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.chamma.R
import com.android.chamma.databinding.ActivityReviewBinding
import com.umc.chamma.ui.home.model.ReviewData
//import com.umc.chamma.R
import com.google.android.material.bottomsheet.BottomSheetDialog

class ReviewActivity : com.umc.chamma.config.BaseActivityVB<ActivityReviewBinding>(ActivityReviewBinding::inflate) {
    private lateinit var bottomSheet: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dataList:ArrayList<ReviewData> = arrayListOf()

        dataList.apply{
            add(
                ReviewData("나는참지않쥐",R.drawable.review_profile,"23.","07.",11,5f,
                "화장실 너무 깨끗해요\n휴지도 넉넉하게 있어서 좋았어요!")
            )
            add(
                ReviewData("읏차",R.drawable.review_profile2,"23.","06.",30,5f,
                "관리가 잘 되어 있어요~")
            )
            add(
                ReviewData("minjji7",R.drawable.review_profile3,"23.","06.",30,4f,
                "냄새 탈취제 있으면 더 좋을거 같음")
            )
            add(
                ReviewData("dkwe32",R.drawable.review_profile4,"23.","06.",28,5f,
                "깔끔해요 화장실 급할 때 이 근처면 여기만 갈 것 같아요")
            )
            add(
                ReviewData("급해",R.drawable.review_profile5,"23.","06.",21,5f,
                "화장실 너무 깨끗해요\n휴지도 넉넉하게 있어서 좋았어요!")
            )
        }
        val dataRVAdapter = ReviewDataAdapter(dataList)

        binding.recyclerView.adapter=dataRVAdapter
        binding.recyclerView.layoutManager= LinearLayoutManager(this)

        binding.optionBtn.setOnClickListener{
            bottomSheet = layoutInflater.inflate(R.layout.fragment_btmshtdialog_sort_list, null)
            val bottomSheetDialog = BottomSheetDialog(this)
            val btnClose = bottomSheet.findViewById<Button>(R.id.btmshtBtnClose)
            val newestTv=bottomSheet.findViewById<Button>(R.id.newestTv)
            val highestTv=bottomSheet.findViewById<Button>(R.id.highestTv)
            val lowTv=bottomSheet.findViewById<Button>(R.id.lowTv)

            // calendar bottomsheetdialog 설정
            bottomSheetDialog.setContentView(bottomSheet)
            bottomSheetDialog.show()

            newestTv.setOnClickListener {
                binding.optionBtn.text="최신순"
            }
            highestTv.setOnClickListener {
                binding.optionBtn.text="별점 높은 순"

            }
            lowTv.setOnClickListener {
                binding.optionBtn.text="별점 낮은 순"

            }
            btnClose.setOnClickListener {
                bottomSheetDialog.dismiss()
            }

            binding.toolBar.setNavigationOnClickListener {
                finish()//왜안되냐!
            }
        }
    }
}