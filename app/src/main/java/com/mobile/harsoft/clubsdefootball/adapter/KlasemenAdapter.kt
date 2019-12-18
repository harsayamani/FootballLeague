package com.mobile.harsoft.clubsdefootball.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobile.harsoft.clubsdefootball.R
import com.mobile.harsoft.clubsdefootball.model.Klasemen
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_klasemen.*

class KlasemenAdapter(
    private val context: Context,
    private val classments: List<Klasemen>,
    private val listener: (Klasemen) -> Unit
) : RecyclerView.Adapter<KlasemenAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(context).inflate(
            R.layout.item_klasemen, parent, false
        )
    )

    override fun getItemCount(): Int = classments.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(classments[holder.adapterPosition], listener)
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bindItem(classments: Klasemen, listener: (Klasemen) -> Unit) {
            val numb = adapterPosition + 1
            number.text = numb.toString()
            club.text = classments.name
            played.text = classments.played.toString()
            win.text = classments.win.toString()
            draw.text = classments.draw.toString()
            loss.text = classments.loss.toString()
            goalsfor.text = classments.goalsfor.toString()
            goalsagainst.text = classments.goalsagainst.toString()
            goalsdiff.text = classments.goalsdifference.toString()
            total.text = classments.total.toString()

            containerView.setOnClickListener {
                listener(classments)
            }
        }
    }
}