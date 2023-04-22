package gb.com.mvp.presenter.main

import com.github.terrakok.cicerone.Router
import gb.com.mvp.view.main.MainView
import gb.com.navigation.IScreens
import moxy.MvpPresenter
import javax.inject.Inject

class MainPresenter: MvpPresenter<MainView>() {

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var screens: IScreens

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(screens.usersScreen())
    }

    fun backClicked() {
        router.exit()
    }

    fun openListFragment() {
        router.navigateTo(screens.usersScreen())
    }

    fun openSearchFragment() {
        router.navigateTo(screens.searchScreen())
    }
}