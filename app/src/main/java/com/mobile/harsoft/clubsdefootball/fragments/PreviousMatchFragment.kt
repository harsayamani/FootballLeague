package com.mobile.harsoft.clubsdefootball.fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.harsoft.clubsdefootball.DetailPrevMatchActivity
import com.mobile.harsoft.clubsdefootball.R
import com.mobile.harsoft.clubsdefootball.adapter.PrevMatchAdapter
import com.mobile.harsoft.clubsdefootball.api.ApiRepository
import com.mobile.harsoft.clubsdefootball.model.Events
import com.mobile.harsoft.clubsdefootball.util.invisible
import com.mobile.harsoft.clubsdefootball.util.visible
import kotlinx.android.synthetic.main.fragment_next_match.progress_bar
import kotlinx.android.synthetic.main.fragment_next_match.swipe
import kotlinx.android.synthetic.main.fragment_previous_match.*
import org.jetbrains.anko.support.v4.onRefresh
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * A simple [Fragment] subclass.
 */
class PreviousMatchFragment : BaseFragment() {

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
        initData()
    }

    private fun initData() {
        val idLeague = ACTIVITY.idLeague

        alert_prev.invisible()
        progress_bar.visible()

        ApiRepository().api().getPrevMatch(idLeague)?.enqueue(object : Callback<Events?> {
            override fun onFailure(call: Call<Events?>, t: Throwable) {
                progress_bar.invisible()
                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Events?>, response: Response<Events?>) {
                val data = response.body()
                try {
                    prev_match_recycler.layoutManager = LinearLayoutManager(context)
                    val adapter =
                        context?.let {
                            PrevMatchAdapter(requireContext(), data?.events!!) {
                                val intent = Intent(context, DetailPrevMatchActivity::class.java)
                                intent.putExtra("match_detail", it)
                                startActivity(intent)
                            }
                        }
                    prev_match_recycler.adapter = adapter
                    adapter?.notifyDataSetChanged()
                    progress_bar.invisible()

                    swipe.onRefresh {
                        prev_match_recycler.layoutManager = LinearLayoutManager(context)
                        prev_match_recycler.adapter = adapter
                        adapter?.notifyDataSetChanged()
                        swipe.isRefreshing = false
                    }
                } catch (e: Exception) {
                    progress_bar.invisible()
                    alert_prev.visible()
                }
            }
        })
    }


}
