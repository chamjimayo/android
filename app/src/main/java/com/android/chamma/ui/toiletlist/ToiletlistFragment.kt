package com.android.chamma.ui.toiletlist

import android.content.Intent
import android.graphics.Paint
import com.android.chamma.R
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.android.chamma.config.BaseFragmentVB
import com.android.chamma.databinding.FragmentToiletListBinding
import me.relex.circleindicator.CircleIndicator3


class ToiletlistFragment : BaseFragmentVB<FragmentToiletListBinding>(FragmentToiletListBinding::bind, R.layout.fragment_toilet_list){
    private var pageItemList = ArrayList<Int>()
    private lateinit var RestroomVPAdapter: RestroomVPAdapter

   // private val viewBinding: FragmentToiletListBinding by lazy{
        //FragmentToiletListBinding.inflate(layoutInflater)
    //}
    /*override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return binding.root
    }


     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       super.onViewCreated(view, savedInstanceState)
       try {
           Log.d("왜", "안돼")
           pageItemList.add(R.drawable.restroom_ex)
           pageItemList.add(R.drawable.restroom_ex)
           pageItemList.add(R.drawable.restroom_ex)
           pageItemList.add(R.drawable.restroom_ex)
           Log.d("왜", "안돼")
           //Log.e("왜",error.toString())

           RestroomVPAdapter = RestroomVPAdapter(pageItemList)
           Log.d("왜", "안돼")

           binding.restroomVp.apply {
               adapter = RestroomVPAdapter
               orientation = ViewPager2.ORIENTATION_HORIZONTAL
           }
           Log.d("왜", "안돼4")

           val indicator: CircleIndicator3 = binding.indicator
           indicator.setViewPager(binding.restroomVp)
           Log.d("왜", "안돼5")

           binding.useBtn.setOnClickListener {
               val intent = Intent(requireContext(), ReviewActivity::class.java)
               startActivity(intent)
           }
           Log.d("왜", "안돼6")

           binding.reviewTv.paintFlags = Paint.UNDERLINE_TEXT_FLAG
           Log.d("왜", "안돼7")

       } catch (e: Exception) {
           Log.d("왜",e.message.toString())
       }
   }
}