package gb.com.mvp.view.fragments.repositoryDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import gb.com.App
import gb.com.databinding.FragmentRepositoryDetailsBinding
import gb.com.mvp.model.entity.GithubUser
import gb.com.mvp.model.entity.GithubUserRepository
import gb.com.mvp.presenter.repositoryDetails.RepositoryDetailsPresenter
import gb.com.mvp.view.main.BackButtonListener
import gb.com.utility.ARG_USER
import gb.com.utility.ARG_USER_REPO
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class RepositoryDetailsFragment: MvpAppCompatFragment(), IRepositoryDetailsView, BackButtonListener{

    private var _binding : FragmentRepositoryDetailsBinding? = null
    private val binding get() = _binding!!

    val presenter: RepositoryDetailsPresenter by moxyPresenter {
        RepositoryDetailsPresenter(App.instance.router)
    }

    companion object {
        fun newInstance(
            user: GithubUser,
            userRepo: GithubUserRepository) = RepositoryDetailsFragment().apply{
            arguments = Bundle().apply {
                putParcelable(ARG_USER_REPO, userRepo)
                putParcelable(ARG_USER, user)
            }
        }
    }

    private var userRepo: GithubUserRepository? = null
    private var user: GithubUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userRepo = it.getParcelable(ARG_USER_REPO)
            user = it.getParcelable(ARG_USER)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentRepositoryDetailsBinding.inflate(inflater, container, false).also{
        _binding = it
    }.root

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun setRepositoryForks() {
        binding.tvRepositoryForks.text = userRepo?.forks.toString()
    }

    override fun backPressed(): Boolean {
        presenter.backPressed()
        return true
    }
}