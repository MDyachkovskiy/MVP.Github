package gb.com.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import gb.com.App
import gb.com.R
import gb.com.mvp.presenter.user.UserPresenter
import gb.com.mvp.view.user.IUserView
import gb.com.ui.BackButtonListener
import gb.com.utility.ARG_LOGIN
import kotlinx.android.synthetic.main.fragment_user.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class UserFragment: MvpAppCompatFragment(), IUserView, BackButtonListener{

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
        UserPresenter(App.instance.router)
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
    ): View? {
        return inflater.inflate(R.layout.fragment_user, container,false)
    }


    override fun backPressed(): Boolean {
        presenter.backPressed()
        return true
    }

    override fun init() {
        login?.let {
            user_login.text = it}?: let { user_login.text = "not found login" }
        }
}