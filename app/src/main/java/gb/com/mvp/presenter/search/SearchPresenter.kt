package gb.com.mvp.presenter.search

import com.github.terrakok.cicerone.Router
import gb.com.di.search.ISearchScopeContainer
import gb.com.mvp.view.fragments.search.ISearchView
import io.reactivex.rxjava3.disposables.Disposable
import moxy.MvpPresenter
import javax.inject.Inject

class SearchPresenter: MvpPresenter<ISearchView>() {

    @Inject lateinit var router: Router

    @Inject lateinit var searchScopeContainer: ISearchScopeContainer

    private var disposable: Disposable? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }

    fun backPressed(): Boolean{
        router.exit()
        disposable?.dispose()
        return true
    }

    override fun onDestroy() {
        searchScopeContainer.releaseSearchScope()
        super.onDestroy()
    }

}