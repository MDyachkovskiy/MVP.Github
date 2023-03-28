package gb.com.mvp.presenter

import com.github.terrakok.cicerone.Router
import gb.com.mvp.view.MainView
import gb.com.navigation.Screens
import moxy.MvpPresenter

class MainPresenter(
    private val router: Router
): MvpPresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        router.replaceScreen(Screens.UsersScreen())
    }

    fun backClicked() {
        router.exit()
    }
}