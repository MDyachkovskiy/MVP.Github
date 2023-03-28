package gb.com.mvp.presenter.user

import com.github.terrakok.cicerone.Router
import gb.com.navigation.Screens
import gb.com.mvp.view.user.IUserView
import moxy.MvpPresenter

class UserPresenter(
    private val router: Router
) : MvpPresenter<IUserView>(){

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
    }

    fun backPressed(): Boolean {
        router.replaceScreen(Screens.UsersScreen())
        return true
    }
}