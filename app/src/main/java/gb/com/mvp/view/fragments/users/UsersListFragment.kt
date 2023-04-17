package gb.com.mvp.view.fragments.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import gb.com.App
import gb.com.databinding.FragmentUsersListBinding
import gb.com.mvp.model.repository.imageLoader.GlideImageLoader
import gb.com.mvp.presenter.users.UsersListPresenter
import gb.com.mvp.view.adapters.users.UsersRVAdapter
import gb.com.mvp.view.main.BackButtonListener
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class UsersListFragment: MvpAppCompatFragment(), IUsersListView, BackButtonListener {

    private var _binding: FragmentUsersListBinding?= null
    private val binding get() = _binding!!

    private var adapter: UsersRVAdapter? = null

    private val presenter: UsersListPresenter by moxyPresenter {
        UsersListPresenter().apply {
            App.instance.appComponent.inject(this)
        }
    }

    companion object{
        fun newInstance() = UsersListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentUsersListBinding.inflate(inflater, container, false).also {
        _binding = it
    }.root

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun init() {
        binding.rvUsers.layoutManager = LinearLayoutManager(context)
        adapter = UsersRVAdapter(presenter.usersListPresenter,
            GlideImageLoader().apply{
                App.instance.appComponent.inject(this)
            })
        binding.rvUsers.adapter = adapter
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

    override fun backPressed() = presenter.backPressed()
}