package com.mobile.harsoft.clubsdefootball

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mobile.harsoft.clubsdefootball.adapter.PagerAdapter
import kotlinx.android.synthetic.main.activity_match_schedule.*

class MatchScheduleActivity : AppCompatActivity() {

    var idLeague = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_schedule)

        matchPager()
        idLeague = intent.getStringExtra("league_id")

    }


    private fun matchPager() {
        vp_match_schedule.adapter = PagerAdapter(supportFragmentManager)
        tabs_main.setupWithViewPager(vp_match_schedule)

    }
}
