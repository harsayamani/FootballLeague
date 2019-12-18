package com.mobile.harsoft.clubsdefootball.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mobile.harsoft.clubsdefootball.R
import com.mobile.harsoft.clubsdefootball.model.Player
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_team_player.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PlayerAdapter(
    private val context: Context,
    private val players: List<Player>,
    private val listener: (Player) -> Unit
) : RecyclerView.Adapter<PlayerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(context).inflate(
            R.layout.item_team_player, parent, false
        )
    )

    override fun getItemCount(): Int = players.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(players[holder.adapterPosition], listener)
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bindItem(players: Player, listener: (Player) -> Unit) {

            GlobalScope.launch(Dispatchers.Main) {
                player_name.text = players.strPlayer
                player_position.text = players.strPosition

                Glide.with(itemView).load(players.strCutout).centerCrop().into(player_picture)
            }

            containerView.setOnClickListener {
                listener(players)
            }
        }
    }
}