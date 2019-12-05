package com.mobile.harsoft.clubsdefootball.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobile.harsoft.clubsdefootball.R
import com.mobile.harsoft.clubsdefootball.model.Match
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_next_match.*

class NextMatchAdapter(
    private val context: Context,
    private val nextMatch: List<Match>,
    private val listener: (Match) -> Unit
) : RecyclerView.Adapter<NextMatchAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(context).inflate(
            R.layout.item_next_match, parent, false
        )
    )

    override fun getItemCount(): Int = nextMatch.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(nextMatch[holder.adapterPosition], listener)
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bindItem(match: Match, listener: (Match) -> Unit) {
            home_club.text = match.strHomeTeam
            away_club.text = match.strAwayTeam

            containerView.setOnClickListener {
                listener(match)
            }
        }
    }
}