package com.mobile.harsoft.clubsdefootball

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.mobile.harsoft.clubsdefootball.api.ApiRepo
import com.mobile.harsoft.clubsdefootball.api.FootballSportAPI
import com.mobile.harsoft.clubsdefootball.database.database
import com.mobile.harsoft.clubsdefootball.model.FavoriteMatch
import com.mobile.harsoft.clubsdefootball.model.Match
import com.mobile.harsoft.clubsdefootball.model.response.ResponseTeams
import com.mobile.harsoft.clubsdefootball.presenter.DetailMatchPresenter
import com.mobile.harsoft.clubsdefootball.util.invisible
import com.mobile.harsoft.clubsdefootball.util.visible
import com.mobile.harsoft.clubsdefootball.view.MatchView
import kotlinx.android.synthetic.main.activity_detail_next_match.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar

class DetailNextMatchActivity : AppCompatActivity(), MatchView {

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var presenter: DetailMatchPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_next_match)
        val matchItem = intent.getParcelableExtra<Match>("match_detail")
        favoriteState(matchItem?.idEvent)
        initMatchData(matchItem)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favorite, menu)
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

    private fun initMatchData(matchItem: Match?) {
        val request = ApiRepo()
        val gson = Gson()

        presenter = DetailMatchPresenter(this, request, gson)
        presenter.getDetailMatch(matchItem?.idEvent)
    }

    private fun getTeamBadge(
        team: String?,
        teamBadge: ImageView
    ) {
        GlobalScope.launch(Dispatchers.Main) {
            val request = ApiRepo()
            val gson = Gson()
            val data = gson.fromJson(
                request.doRequestAsync(FootballSportAPI.getTeams(team)).await(),
                ResponseTeams::class.java
            )

            val badge = data.teams.first().strTeamBadge.toString()

            Glide.with(applicationContext)
                .load(badge)
                .centerCrop()
                .into(teamBadge)
        }
    }

    private fun addToFavorite() {
        try {
            val matchItem = intent.getParcelableExtra<Match>("match_detail")

            database.use {
                insert(
                    FavoriteMatch.TABLE_FAVORITE,
                    FavoriteMatch.EVENT_ID to matchItem?.idEvent,
                    FavoriteMatch.EVENT_NAME to matchItem?.strEvent,
                    FavoriteMatch.EVENT_DATE to matchItem?.dateEvent,
                    FavoriteMatch.EVENT_TIME to matchItem?.strTime,
                    FavoriteMatch.HOME_TEAM to matchItem?.strHomeTeam,
                    FavoriteMatch.HOME_SCORED to matchItem?.intHomeScore,
                    FavoriteMatch.AWAY_TEAM to matchItem?.strAwayTeam,
                    FavoriteMatch.AWAY_SCORED to matchItem?.intAwayScore,
                    FavoriteMatch.SEASON to matchItem?.strSeason,
                    FavoriteMatch.LEAGUE to matchItem?.strLeague,
                    FavoriteMatch.EVENT_FILE to matchItem?.strFileName,
                    FavoriteMatch.DESCRIPTION to matchItem?.strDescription,
                    FavoriteMatch.SPORT to matchItem?.strSport,
                    FavoriteMatch.ID_LEAGUE to matchItem?.idLeague
                )
            }
            relative2.snackbar("Add to Favorite").show()
        } catch (e: SQLiteConstraintException) {
            relative2.snackbar(e.toString()).show()
        }
    }

    private fun removeFromFavorite(idEvent: String?) {
        try {
            database.use {
                delete(
                    FavoriteMatch.TABLE_FAVORITE,
                    "(EVENT_ID={id})",
                    "id" to idEvent.toString()
                )
                relative2.snackbar("Removed to Favorite").show()
            }
        } catch (e: SQLiteConstraintException) {
            relative2.snackbar(e.toString()).show()
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
            val result = select(FavoriteMatch.TABLE_FAVORITE)
                .whereArgs("(EVENT_ID = {id})", "id" to idEvent.toString())
            val favorite = result.parseList(classParser<Match>())
            if (favorite.isNotEmpty()) isFavorite = true
        }
    }

    override fun showAlert() {
        Toast.makeText(this, "Failure", Toast.LENGTH_LONG).show()
    }

    override fun hideAlert() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoading() {
        progress_bar.visible()
    }

    override fun hideLoading() {
        progress_bar.invisible()
    }

    @SuppressLint("SetTextI18n")
    override fun matchData(data: List<Match>) {
        val homeScored = "-"
        val awayScored = "-"
        val seasonLeague = data.first().strSeason
        val date = data.first().dateEvent
        val time = data.first().strTime
        val homeTeam = data.first().strHomeTeam.toString()
        val awayTeam = data.first().strAwayTeam.toString()
        val eventTitle = data.first().strEvent

        event.text = eventTitle
        home_scored.text = homeScored
        away_scored.text = awayScored
        season.text = seasonLeague
        datetime.text = "$date $time"
        getTeamBadge(homeTeam, home_badge)
        getTeamBadge(awayTeam, away_badge)
    }
}
