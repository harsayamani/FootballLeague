package com.mobile.harsoft.clubsdefootball.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mobile.harsoft.clubsdefootball.R
import com.mobile.harsoft.clubsdefootball.model.Team
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_list_teams.*

class TeamAdapter(
    private val context: Context,
    private val teams: List<Team>,
    private val listener: (Team) -> Unit
) : RecyclerView.Adapter<TeamAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(context).inflate(
            R.layout.item_list_teams, parent, false
        )
    )

    override fun getItemCount(): Int = teams.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(teams[holder.adapterPosition], listener)
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bindItem(teams: Team, listener: (Team) -> Unit) {
            team_name.text = teams.strTeam
            team_league.text = teams.strLeague
            Glide.with(itemView).load(teams.strTeamBadge).centerCrop().into(team_badge)

            containerView.setOnClickListener {
                listener(teams)
            }
        }
    }
}