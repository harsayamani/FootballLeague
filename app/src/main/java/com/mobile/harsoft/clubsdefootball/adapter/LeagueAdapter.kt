package com.mobile.harsoft.clubsdefootball.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mobile.harsoft.clubsdefootball.ankolayout.LeagueUI
import com.mobile.harsoft.clubsdefootball.model.LeagueLocal
import kotlinx.android.extensions.LayoutContainer
import org.jetbrains.anko.AnkoContext

class LeagueAdapter(
    private val leagues: List<LeagueLocal>,
    private val listener: (LeagueLocal) -> Unit
) :
    RecyclerView.Adapter<LeagueAdapter.ViewHolder>() {
    override fun getItemCount(): Int = leagues.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(leagues[holder.adapterPosition], listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LeagueUI().createView(
            AnkoContext.Companion.create(parent.context, parent)
        )
    )

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bindItem(leagues: LeagueLocal, listener: (LeagueLocal) -> Unit) {

            val leagueLogo = itemView.findViewById<ImageView>(LeagueUI.id_logo)
            val leagueName = itemView.findViewById<TextView>(LeagueUI.id_name)
            leagueName.text = leagues.league_name

            Glide.with(itemView)
                .load(leagues.league_logo)
                .into(leagueLogo)

            itemView.setOnClickListener {
                listener(leagues)
            }
        }
    }
}
