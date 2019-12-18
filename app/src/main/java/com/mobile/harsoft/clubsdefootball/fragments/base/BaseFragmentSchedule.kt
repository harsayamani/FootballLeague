package com.mobile.harsoft.clubsdefootball.fragments.base

import android.content.Context
import androidx.fragment.app.Fragment
import com.mobile.harsoft.clubsdefootball.MatchScheduleActivity

abstract class BaseFragmentSchedule : Fragment() {
    lateinit var activity: MatchScheduleActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as MatchScheduleActivity
    }
}