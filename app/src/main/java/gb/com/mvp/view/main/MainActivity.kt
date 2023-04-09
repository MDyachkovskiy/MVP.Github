package gb.com.mvp.view.main

import android.os.Bundle
import gb.com.App
import gb.com.R
import gb.com.mvp.presenter.main.MainPresenter
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import com.github.terrakok.cicerone.androidx.AppNavigator
import gb.com.databinding.ActivityMainBinding
import gb.com.navigation.Screens

class MainActivity : MvpAppCompatActivity(), MainView {

    private val navigatorHolder = App.instance.navigatorHolder
    private val navigator = AppNavigator(this, R.id.container)

    private lateinit var binding: ActivityMainBinding

    private val presenter by moxyPresenter {
        MainPresenter(App.instance.router, Screens())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach {
            if (it is BackButtonListener && it.backPressed()){
                return
            }
        }
        presenter.backClicked()
    }
}