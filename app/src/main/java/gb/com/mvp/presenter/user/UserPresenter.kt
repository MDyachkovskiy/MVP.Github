package gb.com.mvp.presenter.user

import com.github.terrakok.cicerone.Router
import gb.com.mvp.model.entity.GithubUser
import gb.com.mvp.view.user.IUserView
import gb.com.navigation.IScreens
import moxy.MvpPresenter

class UserPresenter(
    private val router: Router,
    private val user: GithubUser,
    private val screens: IScreens
) : MvpPresenter<IUserView>(){

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        user.login?.let {viewState.init(it)}

    }

    fun backPressed(): Boolean {
        router.replaceScreen(screens.usersScreen())
        return true
    }
}