package com.mobile.harsoft.clubsdefootball

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.harsoft.clubsdefootball.adapter.LeagueAdapter
import com.mobile.harsoft.clubsdefootball.dataclass.League
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

class MainActivity : AppCompatActivity() {

    companion object {
        const val PARCELABLE_ITEM_LEAGUE = "League"
    }

    private var leagues: MutableList<League> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLeagueData()
        MainActivityUI(leagues).setContentView(this)
    }

    inner class MainActivityUI(private val leagues: List<League>) : AnkoComponent<MainActivity> {
        override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
            verticalLayout {
                lparams(matchParent, wrapContent)

                recyclerView {
                    layoutManager = LinearLayoutManager(context)
                    adapter = LeagueAdapter(leagues) {
                        startActivity<DetailLeagueActivity>(PARCELABLE_ITEM_LEAGUE to it)
                    }
                }
            }
        }
    }

    private fun initLeagueData() {
        val leagueName = resources.getStringArray(R.array.league_name)
        val leagueLogo = resources.obtainTypedArray(R.array.league_logo)
        val leagueDesc = resources.getStringArray(R.array.league_desc)

        leagues.clear()
        for (i in leagueName.indices) {
            leagues.add(League(leagueName[i], leagueLogo.getResourceId(i, 0), leagueDesc[i]))
        }

        leagueLogo.recycle()
    }
}
