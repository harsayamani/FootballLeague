package com.mobile.harsoft.clubsdefootball

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.mobile.harsoft.clubsdefootball.api.ApiRepository
import com.mobile.harsoft.clubsdefootball.model.Events
import com.mobile.harsoft.clubsdefootball.model.Match
import com.mobile.harsoft.clubsdefootball.model.Teams
import com.mobile.harsoft.clubsdefootball.util.invisible
import com.mobile.harsoft.clubsdefootball.util.visible
import kotlinx.android.synthetic.main.activity_detail_prev_match.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPrevMatchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_prev_match)
        val matchItem = intent.getParcelableExtra<Match>("match_detail")
        initMatchData(matchItem, this)
    }

    private fun initMatchData(matchItem: Match?, context: Context) {

        progress_bar.visible()
        ApiRepository().api().getDetailMatch(matchItem?.idEvent)
            ?.enqueue(object : Callback<Events?> {
                override fun onFailure(call: Call<Events?>, t: Throwable) {
                    progress_bar.invisible()
                    Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show()
                }

                @SuppressLint("SetTextI18n")
                override fun onResponse(call: Call<Events?>, response: Response<Events?>) {
                    val data = response.body()
                    val homeScored = data?.events?.first()?.intHomeScore.toString()
                    val awayScored = data?.events?.first()?.intAwayScore.toString()
                    val seasonLeague = data?.events?.first()?.strSeason
                    val date = data?.events?.first()?.dateEvent
                    val time = data?.events?.first()?.strTime
                    val homeTeam = data?.events?.first()?.strHomeTeam
                    val awayTeam = data?.events?.first()?.strAwayTeam
                    val eventTitle = data?.events?.first()?.strEvent

                    event.text = eventTitle
                    home_scored.text = homeScored
                    away_scored.text = awayScored
                    season.text = seasonLeague
                    datetime.text = "$date $time"
                    getTeamBadge(homeTeam, context, home_badge)
                    getTeamBadge(awayTeam, context, away_badge)

                    progress_bar.invisible()
                }
            })
    }

    private fun getTeamBadge(
        team: String?,
        context: Context,
        teamBadge: ImageView
    ) {
        ApiRepository().api().getTeam(team)?.enqueue(object : Callback<Teams?> {
            override fun onFailure(call: Call<Teams?>, t: Throwable) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Teams?>, response: Response<Teams?>) {
                val data = response.body()
                val badge = data?.teams?.first()?.strTeamBadge.toString()
                Glide.with(context)
                    .load(badge)
                    .centerCrop()
                    .into(teamBadge)
            }
        })

    }
}
