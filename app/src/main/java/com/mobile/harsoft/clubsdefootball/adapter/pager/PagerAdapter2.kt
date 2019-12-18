package com.mobile.harsoft.clubsdefootball.adapter.pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.mobile.harsoft.clubsdefootball.fragments.league.DetailLeagueFragment
import com.mobile.harsoft.clubsdefootball.fragments.league.KlasemenFragment
import com.mobile.harsoft.clubsdefootball.fragments.league.TeamsFragment

class PagerAdapter2(fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val pages = listOf(
        DetailLeagueFragment(),
        KlasemenFragment(),
        TeamsFragment()
    )

    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getCount(): Int {
        return pages.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Detail League"
            1 -> "Klasemen"
            else -> "Teams"
        }
    }
}