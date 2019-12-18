package com.mobile.harsoft.clubsdefootball.fragments.match


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.harsoft.clubsdefootball.DetailPrevMatchActivity

import com.mobile.harsoft.clubsdefootball.R
import com.mobile.harsoft.clubsdefootball.adapter.NextMatchAdapter
import com.mobile.harsoft.clubsdefootball.database.database
import com.mobile.harsoft.clubsdefootball.model.FavoriteMatch
import com.mobile.harsoft.clubsdefootball.model.Match
import kotlinx.android.synthetic.main.fragment_favorite_match.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.onRefresh

/**
 * A simple [Fragment] subclass.
 */
class FavoriteMatchFragment : Fragment() {

    private var favorites: MutableList<Match> = mutableListOf()
    private lateinit var adapter: NextMatchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_favorite_match, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = NextMatchAdapter(requireContext(), favorites) {
            val intent = Intent(context, DetailPrevMatchActivity::class.java)
            intent.putExtra("match_detail", it)
            startActivity(intent)
        }

        favorite_recycler.layoutManager = LinearLayoutManager(context)
        favorite_recycler.adapter = adapter

        swipe.onRefresh {
            showFavorite()
        }
    }

    override fun onResume() {
        super.onResume()
        showFavorite()
    }

    private fun showFavorite() {
        swipe.isRefreshing = false
        favorites.clear()
        context?.database?.use {
            val result =
                select(FavoriteMatch.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<Match>())
            favorites.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }
}
