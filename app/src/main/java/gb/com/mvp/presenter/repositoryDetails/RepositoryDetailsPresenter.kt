package gb.com.mvp.presenter.repositoryDetails

import com.github.terrakok.cicerone.Router
import gb.com.mvp.view.fragments.repositoryDetails.IRepositoryDetailsView
import moxy.MvpPresenter

class RepositoryDetailsPresenter(
    private val router: Router
    ): MvpPresenter<IRepositoryDetailsView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.setRepositoryForks()
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}