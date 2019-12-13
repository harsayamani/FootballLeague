package com.mobile.harsoft.clubsdefootball.fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.harsoft.clubsdefootball.DetailLeagueActivity
import com.mobile.harsoft.clubsdefootball.R
import com.mobile.harsoft.clubsdefootball.adapter.LeagueAdapter
import com.mobile.harsoft.clubsdefootball.model.LeagueLocal
import kotlinx.android.synthetic.main.fragment_league.*

/**
 * A simple [Fragment] subclass.
 */
class LeagueFragment : Fragment() {

    private var leagues: MutableList<LeagueLocal> = mutableListOf()
    private lateinit var adapter: LeagueAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_league, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        league_recycler.layoutManager = LinearLayoutManager(context)
        initLeagueData()
        adapter = LeagueAdapter(leagues) {
            val intent = Intent(context, DetailLeagueActivity::class.java)
            intent.putExtra("league_detail", it)
            startActivity(intent)
        }
        league_recycler.adapter = adapter
    }

    private fun initLeagueData() {
        val leagueName = resources.getStringArray(R.array.league_name)
        val leagueLogo = resources.obtainTypedArray(R.array.league_logo)
        val leagueDesc = resources.getStringArray(R.array.league_desc)
        val leagueId = resources.getStringArray(R.array.league_id)

        leagues.clear()
        for (i in leagueName.indices) {
            leagues.add(
                LeagueLocal(
                    leagueId[i],
                    leagueName[i],
                    leagueLogo.getResourceId(i, 0),
                    leagueDesc[i]
                )
            )
        }

        leagueLogo.recycle()
    }
}
