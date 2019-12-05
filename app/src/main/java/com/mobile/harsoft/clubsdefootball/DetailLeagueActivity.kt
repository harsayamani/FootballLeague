package com.mobile.harsoft.clubsdefootball

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.mobile.harsoft.clubsdefootball.model.League
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class DetailLeagueActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val leagueItem = intent.getParcelableExtra<League>(MainActivity.PARCELABLE_ITEM_LEAGUE)
        DetailLeagueActivityUI(leagueItem!!).setContentView(this)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = leagueItem.league_name
    }

    inner class DetailLeagueActivityUI(private val leagues: League) :
        AnkoComponent<DetailLeagueActivity> {

        private val idView = 1
        private val idLogo = 2
        private val idName = 3
        private val idDesc = 4

        override fun createView(ui: AnkoContext<DetailLeagueActivity>) =
            with(ui) {
                scrollView {
                    relativeLayout {
                        lparams(wrapContent, wrapContent)

                        view {
                            id = idView
                            setBackgroundColor(Color.rgb(126, 203, 238))
                        }.lparams(matchParent, dip(150))

                        imageView {
                            id = idLogo
                            Glide.with(this)
                                .load(leagues.league_logo)
                                .into(this)
                        }.lparams(dip(100), dip(100)) {
                            centerHorizontally()
                            topMargin = dip(100)
                        }

                        textView {
                            id = idName
                            text = leagues.league_name
                            textSize = 24f
                            setTypeface(null, Typeface.BOLD)
                        }.lparams {
                            below(idLogo)
                            centerHorizontally()
                        }

                        textView {
                            id = idDesc
                            padding = dip(16)
                            text = leagues.league_desc
                            textAlignment = View.TEXT_ALIGNMENT_CENTER
                        }.lparams {
                            below(idName)
                        }

                        button {
                            text = context.getString(R.string.let_see_schedule)
                            textColor = Color.WHITE
                            backgroundColor = Color.RED
                            onClick {
                                intent = Intent(context, MatchScheduleActivity::class.java)
                                intent.putExtra("league_id", leagues.league_id)
                                intent.putExtra("league_name", leagues.league_name)
                                intent.putExtra("league_desc", leagues.league_desc)
                                intent.putExtra("league_logo", leagues.league_logo)
                                startActivity(intent)
                            }
                            padding = dip(10)
                        }.lparams(width = wrapContent, height = wrapContent) {
                            centerHorizontally()
                            below(idDesc)
                            margin = dip(20)
                        }
                    }
                }
            }
    }
}
