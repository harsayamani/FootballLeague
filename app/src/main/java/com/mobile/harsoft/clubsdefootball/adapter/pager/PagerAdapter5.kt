package com.mobile.harsoft.clubsdefootball.adapter.pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.mobile.harsoft.clubsdefootball.fragments.match.FavoriteMatchFragment
import com.mobile.harsoft.clubsdefootball.fragments.team.FavoriteTeamFragment

class PagerAdapter5(fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val pages = listOf(
        FavoriteMatchFragment(),
        FavoriteTeamFragment()
    )

    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getCount(): Int {
        return pages.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Favorite Match"
            else -> "Favorite Team"
        }
    }
}