package com.mobile.harsoft.clubsdefootball.fragments.league

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.mobile.harsoft.clubsdefootball.MatchScheduleActivity
import com.mobile.harsoft.clubsdefootball.R
import com.mobile.harsoft.clubsdefootball.api.ApiRepo
import com.mobile.harsoft.clubsdefootball.fragments.base.BaseFragmentLeague
import com.mobile.harsoft.clubsdefootball.model.League
import com.mobile.harsoft.clubsdefootball.presenter.DetailLeaguePresenter
import com.mobile.harsoft.clubsdefootball.view.LeagueView
import kotlinx.android.synthetic.main.fragment_detail_league.*

/**
 * A simple [Fragment] subclass.
 */
class DetailLeagueFragment : BaseFragmentLeague(), LeagueView {

    private lateinit var presenter: DetailLeaguePresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_detail_league, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(activity.logoLeague)
        initLeagueData(activity.idLeague)
    }

    private fun initView(leagueLogo: Int) {
        Glide.with(this)
            .load(leagueLogo)
            .centerCrop()
            .into(logo)

        schedule.setOnClickListener {
            val intent = Intent(context, MatchScheduleActivity::class.java)
            intent.putExtra("league_id", activity.idLeague)
            intent.putExtra("league_name", activity.nameLeague)
            intent.putExtra("league_desc", activity.descLeague)
            intent.putExtra("league_logo", activity.logoLeague)
            startActivity(intent)
        }
    }

    private fun initLeagueData(leagueId: String) {
        val request = ApiRepo()
        val gson = Gson()

        presenter = DetailLeaguePresenter(this, request, gson)
        presenter.getDetailLeague(leagueId)
    }


    override fun leagueData(data: List<League?>) {
        name.text = data.first()?.strLeague.toString()
        desc.text = data.first()?.strDescriptionEN.toString()
    }

    override fun showAlert() {
        Toast.makeText(context, "Nothing to Show!", Toast.LENGTH_LONG).show()
    }
}
