package com.mobile.harsoft.clubsdefootball.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobile.harsoft.clubsdefootball.ankolayout.LeagueUI
import com.mobile.harsoft.clubsdefootball.dataclass.League
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import org.jetbrains.anko.AnkoContext

class LeagueAdapter(
    private val leagues: List<League>,
    private val listener: (League) -> Unit
) :
    RecyclerView.Adapter<LeagueAdapter.ViewHolder>() {
    override fun getItemCount(): Int = leagues.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(leagues[position], listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LeagueUI().createView(
            AnkoContext.Companion.create(parent.context, parent)
        )
    )

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bindItem(leagues: League, listener: (League) -> Unit) {

            val league_logo = itemView.findViewById<ImageView>(LeagueUI.id_logo)
            val league_name = itemView.findViewById<TextView>(LeagueUI.id_name)

            league_name.text = leagues.league_name
            leagues.league_logo?.let {
                Picasso.get().load(it).fit().into(league_logo)
            }
            itemView.setOnClickListener {
                listener(leagues)
            }
        }
    }
}
