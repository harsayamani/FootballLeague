package com.mobile.harsoft.clubsdefootball

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.mobile.harsoft.clubsdefootball.adapter.pager.PagerAdapter3
import com.mobile.harsoft.clubsdefootball.database.databaseTeam
import com.mobile.harsoft.clubsdefootball.model.FavoriteTeam
import com.mobile.harsoft.clubsdefootball.model.Team
import kotlinx.android.synthetic.main.activity_detail_team.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar

class DetailTeamActivity : AppCompatActivity() {

    lateinit var idTeam: String
    lateinit var nameTeam: String
    private var isFavorite: Boolean = false
    private var menuItem: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_team)
        val team = intent.getParcelableExtra<Team>("team_detail")
        supportActionBar?.title = team?.strTeam
        favoriteState(team?.idTeam)
        initView(team)
        teamPager()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favorite, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val team = intent.getParcelableExtra<Team>("team_detail")
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.add_to_favorite -> {
                if (isFavorite) removeFromFavorite(team?.idTeam) else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()

                true
            }

            else -> super.onOptionsItemSelected(item!!)
        }
    }

    private fun favoriteState(idTeam: String?) {
        databaseTeam.use {
            val result = select(FavoriteTeam.TABLE_FAVORITE)
                .whereArgs("(TEAM_ID = {id})", "id" to idTeam.toString())
            val favorite = result.parseList(classParser<Team>())
            if (favorite.isNotEmpty()) isFavorite = true
        }
    }

    private fun addToFavorite() {
        try {
            val team = intent.getParcelableExtra<Team>("team_detail")

            databaseTeam.use {
                insert(
                    FavoriteTeam.TABLE_FAVORITE,
                    FavoriteTeam.TEAM_ID to team?.idTeam,
                    FavoriteTeam.TEAM_NAME to team?.strTeam,
                    FavoriteTeam.TEAM_LEAGUE to team?.strLeague,
                    FavoriteTeam.TEAM_BADGE to team?.strTeamBadge,
                    FavoriteTeam.TEAM_SPORT to team?.strSport,
                    FavoriteTeam.TEAM_STADIUM_CAPACITY to team?.intStadiumCapacity,
                    FavoriteTeam.TEAM_STADIUM to team?.strStadium,
                    FavoriteTeam.TEAM_STADIUM_DESC to team?.strStadiumDescription,
                    FavoriteTeam.TEAM_STADIUM_THUMB to team?.strStadiumThumb,
                    FavoriteTeam.TEAM_BANNER to team?.strTeamBanner,
                    FavoriteTeam.TEAM_JERSEY to team?.strTeamJersey,
                    FavoriteTeam.TEAM_ALTERNATE to team?.strAlternate,
                    FavoriteTeam.TEAM_WEBSITE to team?.strWebsite,
                    FavoriteTeam.TEAM_STADIUM_LOC to team?.strStadiumLocation,
                    FavoriteTeam.TEAM_DESC to team?.strDescriptionEN,
                    FavoriteTeam.TEAM_COUNTRY to team?.strCountry
                )
            }
            relative3.snackbar("Add to Favorite").show()
        } catch (e: SQLiteConstraintException) {
            relative3.snackbar(e.toString()).show()
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

    private fun removeFromFavorite(idTeam: String?) {
        try {
            databaseTeam.use {
                delete(
                    FavoriteTeam.TABLE_FAVORITE,
                    "(TEAM_ID={id})",
                    "id" to idTeam.toString()
                )
                relative3.snackbar("Removed to Favorite").show()
            }
        } catch (e: SQLiteConstraintException) {
            relative3.snackbar(e.toString()).show()
        }
    }

    private fun initView(team: Team?) {
        idTeam = team?.idTeam.toString()
        nameTeam = team?.strTeam.toString()
        name.text = team?.strTeam
        Glide.with(this).load(team?.strTeamBanner).into(banner)
        Glide.with(this).load(team?.strTeamBadge).into(badge)
        Glide.with(this).load(team?.strTeamJersey).into(jersey)
    }

    private fun teamPager() {
        vp_team_detail.adapter =
            PagerAdapter3(
                supportFragmentManager
            )
        tabs_main.setupWithViewPager(vp_team_detail)
    }
}
