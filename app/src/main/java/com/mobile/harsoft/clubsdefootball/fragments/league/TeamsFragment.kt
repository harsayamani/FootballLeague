package com.mobile.harsoft.clubsdefootball.fragments.league

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.mobile.harsoft.clubsdefootball.DetailTeamActivity
import com.mobile.harsoft.clubsdefootball.R
import com.mobile.harsoft.clubsdefootball.adapter.TeamAdapter
import com.mobile.harsoft.clubsdefootball.api.ApiRepo
import com.mobile.harsoft.clubsdefootball.fragments.base.BaseFragmentLeague
import com.mobile.harsoft.clubsdefootball.model.Team
import com.mobile.harsoft.clubsdefootball.presenter.TeamsPresenter
import com.mobile.harsoft.clubsdefootball.util.invisible
import com.mobile.harsoft.clubsdefootball.util.visible
import com.mobile.harsoft.clubsdefootball.view.TeamView
import kotlinx.android.synthetic.main.fragment_teams.*
import org.jetbrains.anko.support.v4.onRefresh

/**
 * A simple [Fragment] subclass.
 */
class TeamsFragment : BaseFragmentLeague(), TeamView {

    private var teams: MutableList<Team> = mutableListOf()
    private lateinit var presenter: TeamsPresenter
    private lateinit var adapter: TeamAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_teams, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val idLeague = activity.idLeague
        val request = ApiRepo()
        val gson = Gson()

        presenter = TeamsPresenter(this, request, gson)
        presenter.getAllTeam(idLeague)

        teams_recycler.layoutManager = LinearLayoutManager(context)

        adapter = TeamAdapter(requireContext(), teams) {
            val intent = Intent(context, DetailTeamActivity::class.java)
            intent.putExtra("team_detail", it)
            startActivity(intent)
        }

        teams_recycler.adapter = adapter

        swipe.onRefresh {
            presenter.getAllTeam(idLeague)
            swipe.isRefreshing = false
        }
    }

    override fun showAlert() {
        alert.visible()
    }

    override fun hideAlert() {
        alert.invisible()
    }

    override fun showLoading() {
        progress_bar.visible()
    }

    override fun hideLoading() {
        progress_bar.invisible()
    }

    override fun teamData(data: List<Team>) {
        swipe.isRefreshing = false
        teams.clear()
        teams.addAll(data)
        adapter.notifyDataSetChanged()
    }
}

