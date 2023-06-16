package gb.com.mvp.presenter.repositoryDetails

import com.github.terrakok.cicerone.Router
import gb.com.mvp.model.entity.GithubUser
import gb.com.mvp.view.fragments.repositoryDetails.IRepositoryDetailsView
import gb.com.navigation.IScreens
import moxy.MvpPresenter
import javax.inject.Inject

class RepositoryDetailsPresenter(
    val user: GithubUser
): MvpPresenter<IRepositoryDetailsView>() {

    @Inject lateinit var router: Router
    @Inject lateinit var screens: IScreens

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.setRepositoryForks()
    }

    fun backPressed(): Boolean {
        router.exit()
        //router.backTo(screens.userScreen(user))
        return true
    }
}