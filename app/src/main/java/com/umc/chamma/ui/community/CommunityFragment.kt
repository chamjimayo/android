package com.umc.chamma.ui.community

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.chamma.R
import com.umc.chamma.config.BaseFragmentVB
import com.umc.chamma.databinding.FragmentCommunityBinding
import com.umc.chamma.ui.home.restroomInfo.ReviewDataAdapter

class CommunityFragment : com.umc.chamma.config.BaseFragmentVB<FragmentCommunityBinding>(FragmentCommunityBinding::bind, R.layout.fragment_community){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
 val dataList:ArrayList<CommunityData> = arrayListOf()
        dataList.apply{
            add(
                CommunityData(5362,R.drawable.community_1_1,R.drawable.community_1_2,13,
                    "화장실에서 샤워할 때, 나의 노래 타입은?")
            )
            add(
                CommunityData(5362,R.drawable.community_1_1,R.drawable.community_1_2,13,
                    "화장실에서 샤워할 때, 나의 노래 타입은?")
            )
            add(
                CommunityData(5362,R.drawable.community_1_1,R.drawable.community_1_2,13,
                    "화장실에서 샤워할 때, 나의 노래 타입은?")
            )
            add(
                CommunityData(5362,R.drawable.community_1_1,R.drawable.community_1_2,13,
                    "화장실에서 샤워할 때, 나의 노래 타입은?")
            )
        }
        binding.recyclerView.adapter= CommunityDataAdapter(dataList)
        binding.recyclerView.layoutManager= LinearLayoutManager(context)
    }

}