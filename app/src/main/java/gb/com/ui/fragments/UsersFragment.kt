package gb.com.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import gb.com.App
import gb.com.R
import gb.com.mvp.model.entity.GithubUsersRepo
import gb.com.mvp.presenter.list.UsersPresenter
import gb.com.mvp.view.list.UsersView
import gb.com.ui.BackButtonListener
import gb.com.ui.adapters.UsersRVAdapter
import kotlinx.android.synthetic.main.fragment_users.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class UsersFragment: MvpAppCompatFragment(), UsersView, BackButtonListener {

    private var adapter: UsersRVAdapter? = null
    private val presenter: UsersPresenter by moxyPresenter {
        UsersPresenter(GithubUsersRepo(), App.instance.router)
    }

    companion object{
        fun newInstance() = UsersFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return View.inflate(context, R.layout.fragment_users, null)
    }

    override fun init() {
        rv_users.layoutManager = LinearLayoutManager(context)
        adapter = UsersRVAdapter(presenter.usersListPresenter)
        rv_users.adapter = adapter
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

    override fun backPressed() = presenter.backPressed()
}