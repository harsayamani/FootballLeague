package com.mobile.harsoft.clubsdefootball.fragments.team

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
import com.mobile.harsoft.clubsdefootball.model.Team
import com.mobile.harsoft.clubsdefootball.presenter.SearchTeamsPresenter
import com.mobile.harsoft.clubsdefootball.util.invisible
import com.mobile.harsoft.clubsdefootball.util.visible
import com.mobile.harsoft.clubsdefootball.view.TeamView
import kotlinx.android.synthetic.main.fragment_search_teams.*


/**
 * A simple [Fragment] subclass.
 */
class SearchTeamsFragment : Fragment(), TeamView {

    private var teams: MutableList<Team> = mutableListOf()
    private lateinit var presenter: SearchTeamsPresenter
    private lateinit var adapter: TeamAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_search_teams, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchTeam()
    }

    private fun searchTeam() {
        alert.invisible()
        progress_bar.invisible()
        search_teams.queryHint = "Search Teams"
        search_teams.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                search_teams.setQuery("", false)
                getSearchTeam(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                getSearchTeam(newText)
                return true
            }
        })
    }

    private fun getSearchTeam(query: String?) {
        if (query.isNullOrBlank()) {
            alert.invisible()
        } else {
            val request = ApiRepo()
            val gson = Gson()

            presenter = SearchTeamsPresenter(this, request, gson)
            presenter.getTeam(query)

            search_recycler.layoutManager = LinearLayoutManager(context)

            adapter = TeamAdapter(requireContext(), teams) {
                val intent = Intent(context, DetailTeamActivity::class.java)
                intent.putExtra("team_detail", it)
                startActivity(intent)
            }

            search_recycler.adapter = adapter
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
        try {
            teams.clear()
            for (i in data.indices) {
                if (data[i].strSport == "Soccer") {
                    teams.add(data[i])
                }
            }
            adapter.notifyDataSetChanged()
        } catch (e: Exception) {
            alert.invisible()
        }
    }
}
