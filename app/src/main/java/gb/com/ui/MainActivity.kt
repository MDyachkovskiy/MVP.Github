package gb.com.ui

import android.os.Bundle
import gb.com.App
import gb.com.R
import gb.com.mvp.presenter.MainPresenter
import gb.com.mvp.view.MainView
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import com.github.terrakok.cicerone.androidx.AppNavigator

class MainActivity : MvpAppCompatActivity(), MainView {

    val navigatorHolder = App.instance.navigatorHolder
    val navigator = AppNavigator(this, R.id.container)

    private val presenter by moxyPresenter {
        MainPresenter(App.instance.router)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
        super.onBackPressed()

        supportFragmentManager.fragments.forEach {
            if (it is BackButtonListener && it.backPressed()){
                return
            }
        }

        presenter.backClicked()
    }

}