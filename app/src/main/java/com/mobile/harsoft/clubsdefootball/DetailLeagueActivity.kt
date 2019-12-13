package com.mobile.harsoft.clubsdefootball

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.mobile.harsoft.clubsdefootball.api.ApiRepo
import com.mobile.harsoft.clubsdefootball.model.League
import com.mobile.harsoft.clubsdefootball.model.LeagueLocal
import com.mobile.harsoft.clubsdefootball.presenter.DetailLeaguePresenter
import com.mobile.harsoft.clubsdefootball.view.LeagueView
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class DetailLeagueActivity : AppCompatActivity(), LeagueView {

    private lateinit var presenter: DetailLeaguePresenter

    companion object {
        private const val idView = 1
        private const val idLogo = 2
        private const val idName = 3
        private const val idDesc = 4
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val leagueItem = intent.getParcelableExtra<LeagueLocal>("league_detail")
        DetailLeagueActivityUI(leagueItem!!).setContentView(this)
        initLeagueData(leagueItem)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = leagueItem.league_name
    }

    private fun initLeagueData(leagueItem: LeagueLocal) {
        val request = ApiRepo()
        val gson = Gson()

        presenter = DetailLeaguePresenter(this, request, gson)
        presenter.getDetailLeague(leagueItem.league_id)
    }

    inner class DetailLeagueActivityUI(private val leagues: LeagueLocal) :
        AnkoComponent<DetailLeagueActivity> {

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
                            textSize = 24f
                            setTypeface(null, Typeface.BOLD)
                        }.lparams {
                            below(idLogo)
                            centerHorizontally()
                        }

                        textView {
                            id = idDesc
                            padding = dip(16)
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

    override fun leagueData(data: List<League?>) {
        val leagueName = findViewById<TextView>(idName)
        val leagueDesc = findViewById<TextView>(idDesc)

        leagueName.text = data.first()?.strLeague
        leagueDesc.text = data.first()?.strDescriptionEN
    }

    override fun showAlert() {
        Toast.makeText(this, "Nothing to Show!", Toast.LENGTH_LONG).show()
    }
}
