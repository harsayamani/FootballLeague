package com.mobile.harsoft.clubsdefootball.fragments.match


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.mobile.harsoft.clubsdefootball.DetailPrevMatchActivity
import com.mobile.harsoft.clubsdefootball.R
import com.mobile.harsoft.clubsdefootball.adapter.PrevMatchAdapter
import com.mobile.harsoft.clubsdefootball.api.ApiRepo
import com.mobile.harsoft.clubsdefootball.model.Match
import com.mobile.harsoft.clubsdefootball.presenter.SearchMatchPresenter
import com.mobile.harsoft.clubsdefootball.util.invisible
import com.mobile.harsoft.clubsdefootball.util.visible
import com.mobile.harsoft.clubsdefootball.view.MatchView
import kotlinx.android.synthetic.main.fragment_search_match.*

/**
 * A simple [Fragment] subclass.
 */
class SearchMatchFragment : Fragment(), MatchView {

    private var matches: MutableList<Match> = mutableListOf()
    private lateinit var presenter: SearchMatchPresenter
    private lateinit var adapter: PrevMatchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_search_match, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchMatch()
    }

    private fun searchMatch() {
        alert.invisible()
        progress_bar.invisible()
        search_match.queryHint = "Search Match"
        search_match.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                search_match.setQuery("", false)
                getSearchEvent(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                getSearchEvent(newText)
                return true
            }
        })
    }

    private fun getSearchEvent(query: String?) {
        if (query.isNullOrBlank()) {
            alert.invisible()
        } else {
            val request = ApiRepo()
            val gson = Gson()

            presenter = SearchMatchPresenter(this, request, gson)
            presenter.getMatch(query)

            search_recycler.layoutManager = LinearLayoutManager(context)

            adapter = PrevMatchAdapter(requireContext(), matches) {
                val intent = Intent(context, DetailPrevMatchActivity::class.java)
                intent.putExtra("match_detail", it)
                startActivity(intent)
            }

            search_recycler.adapter = adapter
        }
    }

    override fun showAlert() {
        alert.visible()
    }

    override fun hideAlert() {
        alert.invisible()
    }

    override fun showLoading() {
        progress_bar.visible()
    }

    override fun hideLoading() {
        progress_bar.invisible()
    }

    override fun matchData(data: List<Match>) {
        try {
            matches.clear()
            for (i in data.indices) {
                if (data[i].strSport == "Soccer") {
                    matches.add(data[i])
                }
            }
            adapter.notifyDataSetChanged()
        } catch (e: Exception) {
            Toast.makeText(
                context,
                getString(R.string.nothing_to_show),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
