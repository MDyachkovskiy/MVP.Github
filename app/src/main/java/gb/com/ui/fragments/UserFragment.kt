package gb.com.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import gb.com.App
import gb.com.databinding.FragmentUserBinding
import gb.com.mvp.model.entity.GithubUser
import gb.com.mvp.presenter.user.UserPresenter
import gb.com.mvp.view.user.IUserView
import gb.com.navigation.Screens
import gb.com.ui.activity.BackButtonListener
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class UserFragment(
    private val user: GithubUser
): MvpAppCompatFragment(), IUserView, BackButtonListener {

    private var _binding: FragmentUserBinding?= null
    private val binding get() = _binding!!

    val presenter: UserPresenter by moxyPresenter {
        UserPresenter(App.instance.router, user, Screens())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentUserBinding.inflate(inflater, container,false).also {
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

    override fun init(login: String) {
        user.login.let {
            binding.userLogin.text = it}
        }
}