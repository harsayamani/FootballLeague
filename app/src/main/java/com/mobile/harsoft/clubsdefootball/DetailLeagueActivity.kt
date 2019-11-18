package com.mobile.harsoft.clubsdefootball

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.mobile.harsoft.clubsdefootball.dataclass.League
import org.jetbrains.anko.*

class DetailLeagueActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val leagueItem = intent.getParcelableExtra<League>(MainActivity.PARCELABLE_ITEM_LEAGUE)
        DetailLeagueActivityUI(leagueItem).setContentView(this)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    inner class DetailLeagueActivityUI(private val leagues: League) :
        AnkoComponent<DetailLeagueActivity> {

        private val id_view = 1
        private val id_logo = 2
        private val id_name = 3

        override fun createView(ui: AnkoContext<DetailLeagueActivity>) =
            with(ui) {
                relativeLayout {
                    lparams(wrapContent, wrapContent)

                    view {
                        id = id_view
                        setBackgroundColor(Color.rgb(126, 203, 238))
                    }.lparams(matchParent, dip(150))

                    imageView {
                        id = id_logo
                        Glide.with(this)
                            .load(leagues.league_logo)
                            .into(this)
                    }.lparams(dip(100), dip(100)) {
                        centerHorizontally()
                        topMargin = dip(100)
                    }

                    textView {
                        id = id_name
                        text = leagues.league_name
                        textSize = 24f
                        setTypeface(null, Typeface.BOLD)
                    }.lparams {
                        below(id_logo)
                        centerHorizontally()
                    }

                    textView {
                        padding = dip(16)
                        text = leagues.league_desc
                        textAlignment = View.TEXT_ALIGNMENT_CENTER
                    }.lparams {
                        below(id_name)
                    }
                }
            }

    }
}
