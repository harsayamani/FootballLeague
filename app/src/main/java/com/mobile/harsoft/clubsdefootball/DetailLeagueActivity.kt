package com.mobile.harsoft.clubsdefootball

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mobile.harsoft.clubsdefootball.adapter.pager.PagerAdapter2
import com.mobile.harsoft.clubsdefootball.model.LeagueLocal
import kotlinx.android.synthetic.main.activity_detail_league.*

class DetailLeagueActivity : AppCompatActivity() {

    lateinit var idLeague: String
    lateinit var nameLeague: String
    lateinit var descLeague: String
    var logoLeague: Int = 0
    val context: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_league)
        val leagueItem = intent.getParcelableExtra<LeagueLocal>("league_detail")
        logoLeague = leagueItem?.league_logo!!
        idLeague = leagueItem.league_id.toString()
        nameLeague = leagueItem.league_name.toString()
        descLeague = leagueItem.league_desc.toString()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = leagueItem.league_name
        detailLeaguePager()
    }

    private fun detailLeaguePager() {
        vp_detail_league.adapter =
            PagerAdapter2(
                supportFragmentManager
            )

        dotsIndicator.setViewPager(vp_detail_league)
        vp_detail_league.adapter?.registerDataSetObserver(dotsIndicator.dataSetObserver)
    }
}
