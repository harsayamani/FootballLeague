package com.mobile.harsoft.clubsdefootball.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobile.harsoft.clubsdefootball.R
import com.mobile.harsoft.clubsdefootball.model.Match
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_next_match.away_club
import kotlinx.android.synthetic.main.item_next_match.home_club
import kotlinx.android.synthetic.main.item_prev_match.*

class PrevMatchAdapter(
    private val context: Context,
    private val prevMatch: List<Match>,
    private val listener: (Match) -> Unit
) : RecyclerView.Adapter<PrevMatchAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(context).inflate(
            R.layout.item_prev_match, parent, false
        )
    )

    override fun getItemCount(): Int = prevMatch.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(prevMatch[holder.adapterPosition], listener)
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bindItem(match: Match, listener: (Match) -> Unit) {

            if (match.intHomeScore == null && match.intAwayScore == null) {
                home_club.text = match.strHomeTeam
                away_club.text = match.strAwayTeam
                home_score.text = "-"
                away_score.text = "-"
            } else {
                home_club.text = match.strHomeTeam
                away_club.text = match.strAwayTeam
                home_score.text = match.intHomeScore.toString()
                away_score.text = match.intAwayScore.toString()
            }

            containerView.setOnClickListener {
                listener(match)
            }
        }
    }
}