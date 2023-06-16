package gb.com.mvp.view.fragments.userRepositories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import gb.com.App
import gb.com.databinding.FragmentUserRepositoriesBinding
import gb.com.mvp.model.entity.GithubUser
import gb.com.mvp.presenter.userRepositories.UserRepositoryListPresenter
import gb.com.mvp.view.adapters.userRepositories.UserRepositoriesRVAdapter
import gb.com.mvp.view.main.BackButtonListener
import gb.com.utility.ARG_USER_ONE
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class UserRepositoriesFragment: MvpAppCompatFragment(), IUserRepositoriesView, BackButtonListener {

    lateinit var user: GithubUser

    companion object {
       fun newInstance(user: GithubUser) = UserRepositoriesFragment().apply {
           arguments = Bundle().apply{
               putParcelable(ARG_USER_ONE, user)
           }
       }
    }

    private var _binding: FragmentUserRepositoriesBinding?= null
    private val binding get() = _binding!!

    private var adapter: UserRepositoriesRVAdapter? = null

    val presenter: UserRepositoryListPresenter by moxyPresenter {
        UserRepositoryListPresenter(user).apply {
            App.instance.initRepositorySubcomponent()?.inject(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        user = arguments?.getParcelable<GithubUser>(ARG_USER_ONE) as GithubUser
        super.onCreate(savedInstanceState)
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
            user.let {
                tvId.text = it.id
                tvName.text = it.login
            }
        }
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

    override fun hideProgressBar(){
        binding.progressBar.visibility = View.INVISIBLE
    }
}