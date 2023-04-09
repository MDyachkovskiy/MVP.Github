package gb.com.mvp.presenter.main

import com.github.terrakok.cicerone.Router
import gb.com.mvp.view.main.MainView
import gb.com.navigation.IScreens
import moxy.MvpPresenter

class MainPresenter(
    private val router: Router,
    private val screens: IScreens
): MvpPresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(screens.usersScreen())
    }

    fun backClicked() {
        router.exit()
    }
}