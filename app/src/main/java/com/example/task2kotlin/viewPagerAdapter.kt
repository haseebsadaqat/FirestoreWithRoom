package com.example.task2kotlin

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.task2kotlin.databinding.ActivityMainBinding

class viewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {

        return 3
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return FirebaseFragment.newInstance()
            1 -> return ApiFragment.newInstance()
            2 -> return RoomDbFragment.newInstance()
            else -> return FirebaseFragment.newInstance()
        }

    }

}