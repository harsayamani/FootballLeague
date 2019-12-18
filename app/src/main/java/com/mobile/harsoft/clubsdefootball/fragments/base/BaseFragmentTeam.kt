package com.mobile.harsoft.clubsdefootball.fragments.base

import android.content.Context
import androidx.fragment.app.Fragment
import com.mobile.harsoft.clubsdefootball.DetailTeamActivity

abstract class BaseFragmentTeam : Fragment(){
    lateinit var activity: DetailTeamActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as DetailTeamActivity
    }
}