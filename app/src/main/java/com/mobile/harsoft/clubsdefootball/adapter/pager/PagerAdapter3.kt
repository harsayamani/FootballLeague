package com.mobile.harsoft.clubsdefootball.adapter.pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.mobile.harsoft.clubsdefootball.fragments.team.DetailTeamFragment
import com.mobile.harsoft.clubsdefootball.fragments.team.TeamPlayerFragment

class PagerAdapter3(fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val pages = listOf(
        DetailTeamFragment(),
        TeamPlayerFragment()
    )

    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getCount(): Int {
        return pages.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Info Team"
            else -> "Player"
        }
    }
}