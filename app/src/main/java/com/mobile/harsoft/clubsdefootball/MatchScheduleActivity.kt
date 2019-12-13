package com.mobile.harsoft.clubsdefootball

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mobile.harsoft.clubsdefootball.adapter.PagerAdapter
import kotlinx.android.synthetic.main.activity_match_schedule.*

class MatchScheduleActivity : AppCompatActivity() {

    lateinit var idLeague : String
    var logoLeague : Int = 0
    val context: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_schedule)

        idLeague = intent.getStringExtra("league_id")
        logoLeague = intent.getIntExtra("league_logo", 0)

        matchPager()
        logoInit(logoLeague)

    }

    private fun logoInit(logoLeague: Int?) {
        logo.setImageResource(logoLeague!!)
    }

    private fun matchPager() {
        vp_match_schedule.adapter = PagerAdapter(supportFragmentManager)
        tabs_main.setupWithViewPager(vp_match_schedule)
    }
}
