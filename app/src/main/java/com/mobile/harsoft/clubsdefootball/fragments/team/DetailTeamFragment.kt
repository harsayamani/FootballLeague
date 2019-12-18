package com.mobile.harsoft.clubsdefootball.fragments.team


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.mobile.harsoft.clubsdefootball.R
import com.mobile.harsoft.clubsdefootball.api.ApiRepo
import com.mobile.harsoft.clubsdefootball.fragments.base.BaseFragmentTeam
import com.mobile.harsoft.clubsdefootball.model.Team
import com.mobile.harsoft.clubsdefootball.presenter.DetailTeamPresenter
import com.mobile.harsoft.clubsdefootball.view.TeamView
import kotlinx.android.synthetic.main.fragment_detail_team.*

/**
 * A simple [Fragment] subclass.
 */
class DetailTeamFragment : BaseFragmentTeam(), TeamView {

    private lateinit var presenter: DetailTeamPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_detail_team, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTeamData(activity.idTeam)
    }

    private fun initTeamData(idTeam: String) {
        val request = ApiRepo()
        val gson = Gson()

        presenter = DetailTeamPresenter(this, request, gson)
        presenter.getDetailTeam(idTeam)
    }

    override fun showAlert() {
        Toast.makeText(context, "Nothing to show!", Toast.LENGTH_SHORT).show()
    }

    override fun hideAlert() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun teamData(data: List<Team>) {
        team.text = data.first().strTeam
        desc.text = data.first().strDescriptionEN
        country.text = data.first().strCountry
        stadium.text = data.first().strStadium
        stadium_loc.text = data.first().strStadiumLocation
        stadium_capacity.text = data.first().intStadiumCapacity.toString()
        league.text = data.first().strLeague
    }
}
