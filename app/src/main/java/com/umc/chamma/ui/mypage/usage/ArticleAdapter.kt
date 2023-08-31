package com.umc.chamma.ui.mypage.usage

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.navercorp.nid.NaverIdLoginSDK.applicationContext
import com.umc.chamma.R
import com.umc.chamma.config.App
import com.umc.chamma.databinding.ItemUsageBinding
import com.umc.chamma.ui.mypage.review.model.MypageReviewData
import com.umc.chamma.ui.mypage.review.network.MypageReviewService
import com.umc.chamma.ui.mypage.usage.model.Content

import com.umc.chamma.ui.mypage.usage.model.MypageUsageData
import com.umc.chamma.ui.qr.QrPointResultActivity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ArticleAdapter(
    private val datas : ArrayList<Content>,
) : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding : ItemUsageBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item : Content){
            //이용 후 경과 시간 검사

            //현재 시간 구하기
            fun getTime() : String {
                var now = System.currentTimeMillis()
                var date = Date(now)

                var dateFragment = SimpleDateFormat("MM월 dd일")
                var getTime = dateFragment.format(date)

                return getTime
            }

            //두 날짜 사이의 간격 계산해 텍스트로 변환
            fun intervalBetweenDateText(beforeDate: String) : String{
                val nowFormat = SimpleDateFormat("MM월 dd일").parse(getTime())
                val beforeFormat = SimpleDateFormat("MM월 dd일").parse(beforeDate)

                val diffMilliseconds = nowFormat.time - beforeFormat.time
//                val diffSeconds = diffMilliseconds / 1000
//                val diffMinutes = diffMilliseconds / (60 * 1000)
//                val diffHours = diffMilliseconds / (60 * 60 * 1000)
                val diffDays = diffMilliseconds / (24 * 60 * 60 * 1000)

                val diffDaysString = diffDays.toString()

                return diffDaysString
            }

            val intervalTime = intervalBetweenDateText(item.operatingHour)

            //이용기간이 3일이 넘었으면 비활성화
            fun checkDate(){
                if (intervalTime.toInt() <= 3 ){
                    binding.btnMakeReview.isEnabled = true

                    binding.btnMakeReview.setBackgroundResource(R.drawable.shape_signup_et_focus)
                    binding.tvDateUsageLayout.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.chamma_main))

                    binding.tvDateDate.visibility = View.VISIBLE
                    binding.tvDateLeft.visibility = View.VISIBLE
                    binding.tvDateRight.visibility = View.VISIBLE
                    binding.tvDateMain.visibility = View.VISIBLE

                    binding.tvDateDate.text = intervalTime

                    binding.btnMakeReview.setOnClickListener {
                        val intent = Intent(ViewHolder(binding).itemView?.context, QrPointResultActivity::class.java)
                        ContextCompat.startActivity(ViewHolder(binding).itemView.context, intent, null)
                    }
                }else{
                    binding.btnMakeReview.isEnabled = false

                    binding.btnMakeReview.setBackgroundResource(R.drawable.shape_signup_et)
                    binding.tvDateUsageLayout.text = "리뷰작성 기간 만료"
                    binding.tvDateUsageLayout.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.chamma_signup_textgray))

                    binding.tvDateDate.visibility = View.GONE
                    binding.tvDateLeft.visibility = View.GONE
                    binding.tvDateRight.visibility = View.GONE
                    binding.tvDateMain.visibility = View.GONE
                }
            }

            //리뷰를 작성했는지 안했는지 검사
            fun checkReview() {
                val reviewIdDatas : ArrayList<MypageReviewData>
                var checkReviewId : Boolean = true

//                for (i in 0 until  reviewIdDatas.size){
//                   if (datas[adapterPosition].id == reviewIdDatas[i].reviewId){
//                       checkReviewId = false
//                   }
//                }

                //리뷰를 작성하지 않았을 경우(false -> 이미 리뷰 작성함, true -> 아직 리뷰 작성 안함)
                if (checkReviewId){
                    checkDate()
                }else{
                    binding.btnMakeReview.isEnabled = false

                    binding.tvReviewUsageLayout.setBackgroundResource(R.drawable.shape_signup_et)
                    binding.tvDateUsageLayout.text = "리뷰작성 완료"
                    binding.tvDateUsageLayout.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.chamma_signup_textgray))

                    binding.tvDateDate.visibility = View.GONE
                    binding.tvDateLeft.visibility = View.GONE
                    binding.tvDateRight.visibility = View.GONE
                    binding.tvDateMain.visibility = View.GONE
                }

            }


            // TODO 화장실사진 / 날짜형식 m월 n일 / 화장실 이름
            Glide.with(App.context())
                .load(item.restroomImageUrl[0])
                .error(R.drawable.profile_select_btn)
                .into(binding.ivImageUsageLyout)

            binding.textView12.text= item.point.toString()
//            binding.ratingBar2.rating = item.rating
            binding.tvDateUsageLayout.text = item.operatingHour

            checkReview()



        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding = ItemUsageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int {
        return datas.size
    }
}