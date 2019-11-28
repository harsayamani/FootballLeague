package com.mobile.harsoft.clubsdefootball.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.mobile.harsoft.clubsdefootball.fragments.NextMatchFragment
import com.mobile.harsoft.clubsdefootball.fragments.PreviousMatchFragment

class PagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val pages = listOf(
        PreviousMatchFragment(),
        NextMatchFragment()
    )

    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getCount(): Int {
        return pages.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Previous Match"
            else -> "Next Match"
        }
    }
}