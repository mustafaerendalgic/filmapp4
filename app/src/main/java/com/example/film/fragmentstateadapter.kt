package com.example.film

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class fragmentstateadapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    private val listOfFragments = listOf(
        ShoppingFragment(),
        MyMoviesFragment(),
        hesabimFragment(),
        BrowserFragment()
    )
    override fun getItemCount(): Int {
        return listOfFragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->ShoppingFragment()
            1->BrowserFragment()
            2->MyMoviesFragment()
            3->hesabimFragment()
            else->ShoppingFragment()
        }
    }
}