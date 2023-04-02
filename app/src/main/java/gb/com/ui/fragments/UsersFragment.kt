package gb.com.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import gb.com.App
import gb.com.databinding.FragmentUsersBinding
import gb.com.mvp.model.entity.GithubUsersRepo
import gb.com.mvp.presenter.list.UsersPresenter
import gb.com.mvp.view.list.UsersView
import gb.com.navigation.Screens
import gb.com.ui.activity.BackButtonListener
import gb.com.ui.adapters.UsersRVAdapter
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class UsersFragment: MvpAppCompatFragment(), UsersView, BackButtonListener {

    private var _binding: FragmentUsersBinding?= null
    private val binding get() = _binding!!

    private var adapter: UsersRVAdapter? = null

    private val presenter: UsersPresenter by moxyPresenter {
        UsersPresenter(GithubUsersRepo(), Screens(),  App.instance.router)
    }

    companion object{
        fun newInstance() = UsersFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentUsersBinding.inflate(inflater, container, false).also {
        _binding = it
    }.root

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun init() {
        binding.rvUsers.layoutManager = LinearLayoutManager(context)
        adapter = UsersRVAdapter(presenter.usersListPresenter)
        binding.rvUsers.adapter = adapter
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

    override fun release() {

    }

    override fun backPressed() = presenter.backPressed()
}