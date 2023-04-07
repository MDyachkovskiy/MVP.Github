package gb.com.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import gb.com.App
import gb.com.databinding.FragmentUserBinding
import gb.com.mvp.presenter.user.UserPresenter
import gb.com.mvp.view.user.IUserView
import gb.com.navigation.Screens
import gb.com.ui.activity.BackButtonListener
import gb.com.utility.ARG_LOGIN
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class UserFragment: MvpAppCompatFragment(), IUserView, BackButtonListener {

    private var _binding: FragmentUserBinding?= null
    private val binding get() = _binding!!

    companion object{

        fun newInstance(login: String)
        = UserFragment().apply {
            arguments = Bundle().apply{
                putString(ARG_LOGIN, login)
            }
        }
    }

    private var login: String? = null

    val presenter: UserPresenter by moxyPresenter {
        UserPresenter(App.instance.router, Screens())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            login = it.getString(ARG_LOGIN)
        }
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

    override fun init() {
        login?.let {
            binding.userLogin.text = it}?: let { binding.userLogin.text = "not found login" }
        }
}