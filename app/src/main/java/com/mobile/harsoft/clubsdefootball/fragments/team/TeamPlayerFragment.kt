package com.mobile.harsoft.clubsdefootball.fragments.team


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.mobile.harsoft.clubsdefootball.R
import com.mobile.harsoft.clubsdefootball.adapter.PlayerAdapter
import com.mobile.harsoft.clubsdefootball.api.ApiRepo
import com.mobile.harsoft.clubsdefootball.fragments.base.BaseFragmentTeam
import com.mobile.harsoft.clubsdefootball.model.Player
import com.mobile.harsoft.clubsdefootball.presenter.PlayersPresenter
import com.mobile.harsoft.clubsdefootball.util.invisible
import com.mobile.harsoft.clubsdefootball.util.visible
import com.mobile.harsoft.clubsdefootball.view.PlayerView
import kotlinx.android.synthetic.main.fragment_team_player.*
import org.jetbrains.anko.support.v4.onRefresh

/**
 * A simple [Fragment] subclass.
 */
class TeamPlayerFragment : BaseFragmentTeam(), PlayerView {

    private var players: MutableList<Player> = mutableListOf()
    private lateinit var presenter: PlayersPresenter
    private lateinit var adapter: PlayerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_team_player, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val team = activity.nameTeam
        val request = ApiRepo()
        val gson = Gson()

        presenter = PlayersPresenter(this, request, gson)
        presenter.getPlayers(team)

        player_recycler.layoutManager = LinearLayoutManager(context)

        adapter = PlayerAdapter(requireContext(), players) {
        }

        player_recycler.adapter = adapter

        swipe.onRefresh {
            presenter.getPlayers(team)
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

    override fun playerData(data: List<Player>) {
        swipe.isRefreshing = false
        players.clear()
        players.addAll(data)
        adapter.notifyDataSetChanged()
    }
}
