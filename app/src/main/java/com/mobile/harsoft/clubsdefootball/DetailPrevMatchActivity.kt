package com.mobile.harsoft.clubsdefootball

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.mobile.harsoft.clubsdefootball.api.ApiRepository
import com.mobile.harsoft.clubsdefootball.database.database
import com.mobile.harsoft.clubsdefootball.model.Events
import com.mobile.harsoft.clubsdefootball.model.Favorite
import com.mobile.harsoft.clubsdefootball.model.Match
import com.mobile.harsoft.clubsdefootball.model.Teams
import com.mobile.harsoft.clubsdefootball.util.invisible
import com.mobile.harsoft.clubsdefootball.util.visible
import kotlinx.android.synthetic.main.activity_detail_prev_match.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPrevMatchActivity : AppCompatActivity() {

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_prev_match)
        val matchItem = intent.getParcelableExtra<Match>("match_detail")
        favoriteState(matchItem?.idEvent)
        initMatchData(matchItem, this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favorite_match, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val matchItem = intent.getParcelableExtra<Match>("match_detail")
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.add_to_favorite -> {
                if (isFavorite) removeFromFavorite(matchItem?.idEvent) else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()

                true
            }

            else -> super.onOptionsItemSelected(item!!)
        }
    }

    private fun initMatchData(matchItem: Match?, context: Context) {

        progress_bar.visible()
        ApiRepository().api().getDetailMatch(matchItem?.idEvent)
            ?.enqueue(object : Callback<Events?> {
                override fun onFailure(call: Call<Events?>, t: Throwable) {
                    progress_bar.invisible()
                    Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<Events?>, response: Response<Events?>) {
                    val data = response.body()

                    if (data != null) {
                        val homeScored = data.events.first().intHomeScore.toString()
                        val awayScored = data.events.first().intAwayScore.toString()
                        val seasonLeague = data.events.first().strSeason
                        val date = data.events.first().dateEvent
                        val time = data.events.first().strTime
                        val homeTeam = data.events.first().strHomeTeam
                        val awayTeam = data.events.first().strAwayTeam
                        val eventTitle = data.events.first().strEvent

                        event.text = eventTitle
                        home_scored.text = homeScored
                        away_scored.text = awayScored
                        season.text = seasonLeague
                        datetime.text = "$date $time"
                        getTeamBadge(homeTeam, context, home_badge)
                        getTeamBadge(awayTeam, context, away_badge)
                        progress_bar.invisible()
                    } else {
                        progress_bar.invisible()
                        Toast.makeText(
                            context,
                            getString(R.string.doesnt_match_showing),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
    }

    private fun getTeamBadge(
        team: String?,
        context: Context,
        teamBadge: ImageView
    ) {
        ApiRepository().api().getTeam(team)?.enqueue(object : Callback<Teams?> {
            override fun onFailure(call: Call<Teams?>, t: Throwable) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Teams?>, response: Response<Teams?>) {
                val data = response.body()
                val badge = data?.teams?.first()?.strTeamBadge.toString()
                Glide.with(applicationContext)
                    .load(badge)
                    .centerCrop()
                    .into(teamBadge)
            }
        })
    }

    private fun addToFavorite() {
        try {
            val matchItem = intent.getParcelableExtra<Match>("match_detail")

            database.use {
                insert(
                    Favorite.TABLE_FAVORITE,
                    Favorite.EVENT_ID to matchItem?.idEvent,
                    Favorite.EVENT_NAME to matchItem?.strEvent,
                    Favorite.EVENT_DATE to matchItem?.dateEvent,
                    Favorite.EVENT_TIME to matchItem?.strTime,
                    Favorite.HOME_TEAM to matchItem?.strHomeTeam,
                    Favorite.HOME_SCORED to matchItem?.intHomeScore,
                    Favorite.AWAY_TEAM to matchItem?.strAwayTeam,
                    Favorite.AWAY_SCORED to matchItem?.intAwayScore,
                    Favorite.SEASON to matchItem?.strSeason,
                    Favorite.LEAGUE to matchItem?.strLeague,
                    Favorite.EVENT_FILE to matchItem?.strFileName,
                    Favorite.DESCRIPTION to matchItem?.strDescription,
                    Favorite.SPORT to matchItem?.strSport,
                    Favorite.ID_LEAGUE to  matchItem?.idLeague
                )
            }
            relative1.snackbar("Add to Favorite").show()
        } catch (e: SQLiteConstraintException) {
            relative1.snackbar(e.toString()).show()
        }
    }

    private fun removeFromFavorite(idEvent: String?) {
        try {
            database.use {
                delete(
                    Favorite.TABLE_FAVORITE,
                    "(EVENT_ID={id})",
                    "id" to idEvent.toString()
                )
                relative1.snackbar("Removed to Favorite").show()
            }
        } catch (e: SQLiteConstraintException) {
            relative1.snackbar(e.toString()).show()
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)!!.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_favorite_white_24dp)
        else if (!isFavorite)
            menuItem?.getItem(0)!!.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_white_24dp)
    }

    private fun favoriteState(idEvent: String?) {
        database.use {
            val result = select(Favorite.TABLE_FAVORITE)
                .whereArgs("(EVENT_ID = {id})", "id" to idEvent.toString())
            val favorite = result.parseList(classParser<Match>())
            if (favorite.isNotEmpty()) isFavorite = true
        }
    }
}
