package gb.com.mvp.view.fragments.userRepositories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import gb.com.App
import gb.com.databinding.FragmentUserRepositoriesBinding
import gb.com.mvp.model.api.ApiHolder
import gb.com.mvp.model.entity.GithubUser
import gb.com.mvp.model.network.AndroidNetworkStatus
import gb.com.mvp.model.repository.userRepository.RetrofitGithubUserRepository
import gb.com.mvp.model.room.Database
import gb.com.mvp.presenter.userRepositories.UserRepositoryListPresenter
import gb.com.mvp.view.adapters.userRepositories.UserRepositoriesRVAdapter
import gb.com.navigation.Screens
import gb.com.mvp.view.main.BackButtonListener
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class UserRepositoriesFragment(
    private val user: GithubUser
): MvpAppCompatFragment(), IUserRepositoriesView, BackButtonListener {

    private var _binding: FragmentUserRepositoriesBinding?= null
    private val binding get() = _binding!!

    private var adapter: UserRepositoriesRVAdapter? = null

    val presenter: UserRepositoryListPresenter by moxyPresenter {
        UserRepositoryListPresenter(
            AndroidSchedulers.mainThread(),
            RetrofitGithubUserRepository(ApiHolder.api, AndroidNetworkStatus(App.instance),
                Database.getInstance()),
            App.instance.router, user, Screens())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentUserRepositoriesBinding.inflate(inflater, container,false).also {
        _binding = it
    }.root

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun backPressed(): Boolean {
        presenter.backPressed()
        return true
    }

    override fun init() {
        with (binding) {
            rvRepositories.layoutManager = LinearLayoutManager(context)
            adapter = UserRepositoriesRVAdapter(presenter.userRepositoryListPresenter)
            rvRepositories.adapter = adapter
            tvId.text = user.id
            tvName.text = user.login
        }
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

    override fun hideProgressBar(){
        binding.progressBar.visibility = View.INVISIBLE
    }
}