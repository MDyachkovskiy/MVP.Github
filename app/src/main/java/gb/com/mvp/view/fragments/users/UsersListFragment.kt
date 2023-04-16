package gb.com.mvp.view.fragments.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import gb.com.App
import gb.com.databinding.FragmentUsersListBinding
import gb.com.mvp.model.entity.room.cache.RoomGithubAvatarCache
import gb.com.mvp.model.network.AndroidNetworkStatus
import gb.com.mvp.model.repository.avatar.AvatarFile
import gb.com.mvp.model.repository.imageLoader.GlideImageLoader
import gb.com.mvp.model.room.Database
import gb.com.mvp.presenter.users.UsersListPresenter
import gb.com.mvp.view.adapters.users.UsersRVAdapter
import gb.com.mvp.view.main.BackButtonListener
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject

class UsersListFragment: MvpAppCompatFragment(), IUsersListView, BackButtonListener {

    private var _binding: FragmentUsersListBinding?= null
    private val binding get() = _binding!!

    private var adapter: UsersRVAdapter? = null

    @Inject
    lateinit var database: Database

    private val presenter: UsersListPresenter by moxyPresenter {
        UsersListPresenter(AndroidSchedulers.mainThread()).apply {
            App.instance.appComponent.inject(this)
        }
    }

    companion object{
        fun newInstance() = UsersListFragment().apply{
            App.instance.appComponent.inject(this)
        }
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
            GlideImageLoader(database,
                RoomGithubAvatarCache(AvatarFile()),
                AndroidNetworkStatus(App.instance),
                AndroidSchedulers.mainThread())
        )
        binding.rvUsers.adapter = adapter
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

    override fun backPressed() = presenter.backPressed()
}