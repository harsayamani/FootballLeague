package com.mobile.harsoft.clubsdefootball.fragments

import android.content.Context
import androidx.fragment.app.Fragment
import com.mobile.harsoft.clubsdefootball.MatchScheduleActivity

abstract class BaseFragment : Fragment() {
    lateinit var activity: MatchScheduleActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as MatchScheduleActivity
    }
}