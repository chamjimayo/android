package com.umc.chamma.ui.home.restroomInfo


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
//import com.android.chamma.R
import com.umc.chamma.R
import com.umc.chamma.databinding.ActivityReviewBinding
import com.umc.chamma.ui.home.restroomInfo.model.ReviewData
import com.umc.chamma.ui.home.restroomInfo.model.ReviewData2
import com.umc.chamma.ui.home.restroomInfo.model.ReviewResponse
import com.umc.chamma.util.BottomSheet

class ReviewActivity : com.umc.chamma.config.BaseActivityVB<ActivityReviewBinding>(ActivityReviewBinding::inflate),
ReviewActivityInterface{
    private lateinit var bottomSheet: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val Id= intent.getIntExtra("ID",0)

        binding.toolBar.setNavigationOnClickListener {
            finish()//왜안되냐!->잘못된 범위의 괄호안에다 넣음
        }

       /* val dataList:ArrayList<ReviewData> = arrayListOf()
        val userProfile:String="https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMzA3MTVfMjc0%2FMDAxNjg5NDA5MDM1MDM3.Uv_DyZ81UEFmkCfbrJFAVxRnqTkTqM8ShJKa7_gzWE8g.JvUumf3Vtn_mW527CBLh0D29JprKWvC5C0FZBFy5i8Yg.JPEG.wjatlaajrwk%2F_MG_0121.jpg&type=sc960_832"
        dataList.apply{
            add(
                ReviewData("23.07.11",5,0,"화장실 너무 깨끗해요\n휴지도 넉넉하게 있어서 좋았어요!",
                    0,0,userProfile,"나는참지않쥐")
            )
            add(
                ReviewData("23.06.30",5,0,"관리가 잘 되어 있어요~",
                0,0,userProfile,"읏차")
            )
            add(
                ReviewData("23.06.30",4,0,"냄새 탈취제 있으면 더 좋을거 같음",
                0,0,userProfile,"minjji7")
            )
            add(
                ReviewData("23.06.28",5,0,"깔끔해요 화장실 급할 때 이 근처면 여기만 갈 것 같아요",
                0,0,userProfile,"dkwe32")
            )
            add(
                ReviewData("23.06.21",5,0,"화장실 너무 깨끗해요\n" +
                        "휴지도 넉넉하게 있어서 좋았어요!", 0,0,userProfile,"급해")
            )
        }
*/
        ReviewService(this).tryToGetReviewListLatest(Id);
        /*val dataRVAdapter = ReviewDataAdapter(dataList)

        binding.recyclerView.adapter=dataRVAdapter
        binding.recyclerView.layoutManager= LinearLayoutManager(this)
*/
        binding.optionBtn.setOnClickListener{
            BottomSheet.reviewSequence(this,binding, ReviewService(this),Id)
        }
    }

    override fun onTryToGetRL_HRSuccess(response: ReviewResponse) {
        Log.d("리뷰결과","HR "+response.toString())
        binding.reviewNumTv.text=response.data.size.toString()
        binding.recyclerView.adapter=ReviewDataAdapter(response.data)
        binding.recyclerView.layoutManager= LinearLayoutManager(this)

    }

    override fun onTryToGetRL_HRFailure(message: String) {
        Log.d("리뷰결과","HR "+message)

    }

    override fun onTryToGetRL_LatestSuccess(response: ReviewResponse) {
        Log.d("리뷰결과","Latest "+response.toString())
        binding.reviewNumTv.text=response.data.size.toString()

        binding.recyclerView.adapter=ReviewDataAdapter(response.data)
        binding.recyclerView.layoutManager= LinearLayoutManager(this)
    }
/*
ReviewResponse(data=[ReviewData(dateTime=2023-08-12, rating=5, restroomId=2, reviewContent=테스트 리뷰입니다., reviewId=414, userId=3),
ReviewData(dateTime=2023-08-12, rating=2, restroomId=2, reviewContent=테스트 리뷰입니다., reviewId=10, userId=3),
ReviewData(dateTime=2023-08-12, rating=4, restroomId=2, reviewContent=테스트 리뷰입니다., reviewId=9, userId=3),
ReviewData(dateTime=2023-08-12, rating=5, restroomId=2, reviewContent=테스트 리뷰입니다., reviewId=8, userId=3),
ReviewData(dateTime=2023-08-12, rating=5, restroomId=2, reviewContent=테스트 리뷰입니다., reviewId=7, userId=3),
ReviewData(dateTime=2023-08-12, rating=2, restroomId=2, reviewContent=테스트 리뷰입니다., reviewId=6, userId=3)])
 */
    override fun onTryToGetRL_LatestFailure(message: String) {
        Log.d("리뷰결과","Latest "+message)
    }

    override fun onTryToGetRL_LRSuccess(response: ReviewResponse) {
        Log.d("리뷰결과","LR "+response.toString())
        binding.reviewNumTv.text=response.data.size.toString()

        binding.recyclerView.adapter=ReviewDataAdapter(response.data)
        binding.recyclerView.layoutManager= LinearLayoutManager(this)
    }

    override fun onTryToGetRL_LRFailure(message: String) {
        Log.d("리뷰결과","LR "+message)
    }

}