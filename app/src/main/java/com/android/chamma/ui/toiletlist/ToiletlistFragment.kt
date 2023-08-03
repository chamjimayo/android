package com.android.chamma.ui.toiletlist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.android.chamma.R
import com.android.chamma.config.BaseFragmentVB
import com.android.chamma.databinding.FragmentToiletListBinding
import com.example.weeks6.RestroomVPAdapter
import me.relex.circleindicator.CircleIndicator3

class ToiletlistFragment : BaseFragmentVB<FragmentToiletListBinding>(FragmentToiletListBinding::bind, R.layout.fragment_toilet_list){
    private var pageItemList = ArrayList<Int>()
    private lateinit var RestroomVPAdapter: RestroomVPAdapter

   // private val viewBinding: FragmentToiletListBinding by lazy{
        //FragmentToiletListBinding.inflate(layoutInflater)
    //}
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pageItemList.add(R.drawable.restroom_ex)
        pageItemList.add(R.drawable.restroom_ex)
        pageItemList.add(R.drawable.restroom_ex)
        pageItemList.add(R.drawable.restroom_ex)

        RestroomVPAdapter=RestroomVPAdapter(pageItemList)

        binding.restroomVp.apply {
            adapter=RestroomVPAdapter
            orientation= ViewPager2.ORIENTATION_HORIZONTAL
        }

        val indicator: CircleIndicator3 =binding.indicator
        indicator.setViewPager(binding.restroomVp)

        binding.useBtn.setOnClickListener {
            val intent= Intent(requireContext(), ReviewActivity::class.java)
            startActivity(intent)
        }
    }

}