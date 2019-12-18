package com.mobile.harsoft.clubsdefootball.adapter.pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.mobile.harsoft.clubsdefootball.fragments.match.FavoriteMatchLeagueFragment
import com.mobile.harsoft.clubsdefootball.fragments.match.NextMatchFragment
import com.mobile.harsoft.clubsdefootball.fragments.match.PreviousMatchFragment

class PagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val pages = listOf(
        PreviousMatchFragment(),
        NextMatchFragment(),
        FavoriteMatchLeagueFragment()
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
            1 -> "Next Match"
            else -> "Favorite Match"
        }
    }
}