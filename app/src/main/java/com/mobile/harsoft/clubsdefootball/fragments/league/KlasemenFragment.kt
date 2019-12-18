package com.mobile.harsoft.clubsdefootball.fragments.league


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.mobile.harsoft.clubsdefootball.R
import com.mobile.harsoft.clubsdefootball.adapter.KlasemenAdapter
import com.mobile.harsoft.clubsdefootball.api.ApiRepo
import com.mobile.harsoft.clubsdefootball.fragments.base.BaseFragmentLeague
import com.mobile.harsoft.clubsdefootball.model.Klasemen
import com.mobile.harsoft.clubsdefootball.presenter.KlasemenPresenter
import com.mobile.harsoft.clubsdefootball.view.KlasemenView
import kotlinx.android.synthetic.main.fragment_klasemen.*

/**
 * A simple [Fragment] subclass.
 */
class KlasemenFragment : BaseFragmentLeague(), KlasemenView {

    private var klasemens: MutableList<Klasemen> = mutableListOf()
    private lateinit var presenter: KlasemenPresenter
    private lateinit var adapter: KlasemenAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_klasemen, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(this).load(activity.logoLeague).centerCrop().into(logo)

        val idLeague = activity.idLeague
        val request = ApiRepo()
        val gson = Gson()

        presenter = KlasemenPresenter(this, request, gson)
        presenter.getKlasemen(idLeague)

        klasemen_recycler.layoutManager = LinearLayoutManager(context)

        adapter = KlasemenAdapter(requireContext(), klasemens) {}

        klasemen_recycler.adapter = adapter
    }

    override fun klasemenData(data: List<Klasemen>) {
        klasemens.clear()
        klasemens.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun showAlert() {
        Toast.makeText(context, "Nothing to show!", Toast.LENGTH_SHORT).show()
    }
}
