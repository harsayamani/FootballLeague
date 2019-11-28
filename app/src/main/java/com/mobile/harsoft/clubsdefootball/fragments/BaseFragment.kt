package com.mobile.harsoft.clubsdefootball.fragments

import android.content.Context
import androidx.fragment.app.Fragment
import com.mobile.harsoft.clubsdefootball.MatchScheduleActivity

abstract class BaseFragment : Fragment() {
    lateinit var ACTIVITY: MatchScheduleActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ACTIVITY = context as MatchScheduleActivity
    }
}