package com.mobile.harsoft.clubsdefootball.fragments.match

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.mobile.harsoft.clubsdefootball.DetailPrevMatchActivity
import com.mobile.harsoft.clubsdefootball.R
import com.mobile.harsoft.clubsdefootball.adapter.PrevMatchAdapter
import com.mobile.harsoft.clubsdefootball.api.ApiRepo
import com.mobile.harsoft.clubsdefootball.fragments.base.BaseFragmentSchedule
import com.mobile.harsoft.clubsdefootball.model.Match
import com.mobile.harsoft.clubsdefootball.presenter.PreviousMatchPresenter
import com.mobile.harsoft.clubsdefootball.util.invisible
import com.mobile.harsoft.clubsdefootball.util.visible
import com.mobile.harsoft.clubsdefootball.view.MatchView
import kotlinx.android.synthetic.main.fragment_previous_match.*
import org.jetbrains.anko.support.v4.onRefresh

/**
 * A simple [Fragment] subclass.
 */
class PreviousMatchFragment : BaseFragmentSchedule(), MatchView {

    private var matches: MutableList<Match> = mutableListOf()
    private lateinit var presenter: PreviousMatchPresenter
    private lateinit var adapter: PrevMatchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_previous_match, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val idLeague = activity.idLeague
        val request = ApiRepo()
        val gson = Gson()

        presenter = PreviousMatchPresenter(this, request, gson)
        presenter.getPrevMatch(idLeague)

        prev_match_recycler.layoutManager = LinearLayoutManager(context)

        adapter = PrevMatchAdapter(requireContext(), matches) {
            val intent = Intent(context, DetailPrevMatchActivity::class.java)
            intent.putExtra("match_detail", it)
            startActivity(intent)
        }

        prev_match_recycler.adapter = adapter

        swipe.onRefresh {
            presenter.getPrevMatch(idLeague)
            swipe.isRefreshing = false
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
        swipe.isRefreshing = false
        matches.clear()
        matches.addAll(data)
        adapter.notifyDataSetChanged()
    }
}
