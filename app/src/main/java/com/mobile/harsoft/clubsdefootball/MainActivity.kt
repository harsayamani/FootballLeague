package com.mobile.harsoft.clubsdefootball

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobile.harsoft.clubsdefootball.adapter.LeagueAdapter
import com.mobile.harsoft.clubsdefootball.adapter.PrevMatchAdapter
import com.mobile.harsoft.clubsdefootball.api.ApiRepository
import com.mobile.harsoft.clubsdefootball.model.League
import com.mobile.harsoft.clubsdefootball.model.Match
import com.mobile.harsoft.clubsdefootball.model.Search
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    companion object {
        const val PARCELABLE_ITEM_LEAGUE = "League"
        const val id_recycler = 1
    }

    private var leagues: MutableList<League> = mutableListOf()
    private var matches: MutableList<Match> = ArrayList()
    private val context: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLeagueData()
        MainActivityUI(leagues).setContentView(this)
    }

    inner class MainActivityUI(private val leagues: List<League>) : AnkoComponent<MainActivity> {
        override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
            relativeLayout {
                lparams(matchParent, wrapContent)

                recyclerView {
                    id = id_recycler
                    layoutManager = LinearLayoutManager(context)
                    adapter = LeagueAdapter(leagues) {
                        startActivity<DetailLeagueActivity>(PARCELABLE_ITEM_LEAGUE to it)
                    }
                }.lparams(width = matchParent, height = wrapContent)
            }
        }
    }

    private fun initLeagueData() {
        val leagueName = resources.getStringArray(R.array.league_name)
        val leagueLogo = resources.obtainTypedArray(R.array.league_logo)
        val leagueDesc = resources.getStringArray(R.array.league_desc)
        val leagueId = resources.getStringArray(R.array.league_id)

        leagues.clear()
        for (i in leagueName.indices) {
            leagues.add(
                League(
                    leagueId[i],
                    leagueName[i],
                    leagueLogo.getResourceId(i, 0),
                    leagueDesc[i]
                )
            )
        }

        leagueLogo.recycle()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_match, menu)
        val searchItem = menu?.findItem(R.id.search)
        val searchView = searchItem?.actionView as SearchView
        searchView.queryHint = "Search Match"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.setQuery("", false)
                searchItem.collapseActionView()
                getSearchEvent(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                getSearchEvent(newText)
                return true
            }
        })
        return true
    }

    private fun getSearchEvent(query: String?) {
        if (query.isNullOrBlank() || query == " ") {
            initLeagueData()
            MainActivityUI(leagues).setContentView(this)
        } else {
            ApiRepository().api().getSearchEvent(query)?.enqueue(object : Callback<Search?> {
                override fun onFailure(call: Call<Search?>, t: Throwable) {
                    Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<Search?>, response: Response<Search?>) {
                    val data = response.body()
                    try {

                        val recycler = findViewById<RecyclerView>(id_recycler)

                        recycler.layoutManager = LinearLayoutManager(context)
                        matches.clear()
                        for (i in data?.event?.indices!!) {
                            if (data.event[i].strSport == "Soccer") {
                                matches.add(data.event[i])
                                val adapter =
                                    PrevMatchAdapter(context, matches) {
                                        val intent =
                                            Intent(context, DetailPrevMatchActivity::class.java)
                                        intent.putExtra("match_detail", it)
                                        startActivity(intent)
                                    }
                                recycler.adapter = adapter
                            }
                        }
                    } catch (e: Exception) {
                        Toast.makeText(
                            context,
                            getString(R.string.doesnt_match_showing),
                            Toast.LENGTH_SHORT
                        )
                    }
                }
            })
        }
    }
}
