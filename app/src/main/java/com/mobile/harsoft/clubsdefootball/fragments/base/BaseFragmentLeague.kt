package com.mobile.harsoft.clubsdefootball.fragments.base

import android.content.Context
import androidx.fragment.app.Fragment
import com.mobile.harsoft.clubsdefootball.DetailLeagueActivity

abstract class BaseFragmentLeague : Fragment() {
    lateinit var activity: DetailLeagueActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as DetailLeagueActivity
    }
}
