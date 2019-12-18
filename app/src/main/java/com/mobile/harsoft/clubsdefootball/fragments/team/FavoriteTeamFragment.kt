package com.mobile.harsoft.clubsdefootball.fragments.team

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.harsoft.clubsdefootball.DetailTeamActivity
import com.mobile.harsoft.clubsdefootball.R
import com.mobile.harsoft.clubsdefootball.adapter.TeamAdapter
import com.mobile.harsoft.clubsdefootball.database.databaseTeam
import com.mobile.harsoft.clubsdefootball.model.FavoriteTeam
import com.mobile.harsoft.clubsdefootball.model.Team
import kotlinx.android.synthetic.main.fragment_favorite_team.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.onRefresh

/**
 * A simple [Fragment] subclass.
 */
class FavoriteTeamFragment : Fragment() {

    private var favorites: MutableList<Team> = mutableListOf()
    private lateinit var adapter: TeamAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_favorite_team, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = TeamAdapter(requireContext(), favorites) {
            val intent = Intent(context, DetailTeamActivity::class.java)
            intent.putExtra("team_detail", it)
            startActivity(intent)
        }

        favorite_recycler.layoutManager = LinearLayoutManager(context)
        favorite_recycler.adapter = adapter

        swipe.onRefresh {
            showFavorite()
        }
    }

    override fun onResume() {
        super.onResume()
        showFavorite()
    }

    private fun showFavorite() {
        swipe.isRefreshing = false
        favorites.clear()
        context?.databaseTeam?.use {
            val result = select(FavoriteTeam.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<Team>())
            favorites.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }
}
