package gb.com.mvp.presenter.search

import android.util.Log
import androidx.annotation.VisibleForTesting
import com.github.terrakok.cicerone.Router
import gb.com.di.search.ISearchScopeContainer
import gb.com.mvp.model.entity.GithubUser
import gb.com.mvp.model.entity.searchResponse.User
import gb.com.mvp.model.repository.searchRepository.IGithubUserSearchResult
import gb.com.mvp.view.adapters.searchResult.ISearchResultItemView
import gb.com.mvp.view.fragments.search.ISearchView
import gb.com.navigation.IScreens
import gb.com.utility.TAG
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter
import javax.inject.Inject

class SearchPresenter: MvpPresenter<ISearchView>() {

    @Inject lateinit var router: Router

    @Inject lateinit var screens: IScreens

    @Inject lateinit var searchScopeContainer: ISearchScopeContainer

    @Inject lateinit var searchRepository: IGithubUserSearchResult

    @Inject lateinit var uiScheduler: Scheduler

    private var disposable: Disposable? = null

    var searchResultListPresenter = SearchResultListPresenter()

    class SearchResultListPresenter: ISearchResultListPresenter{
        val users = mutableListOf<User>()

        override var itemClickListener: ((ISearchResultItemView) -> Unit)? = null

        override fun bindView(view: ISearchResultItemView) {
            val user = users[view.pos]
            user.login.let {view.setLogin(it)}
            user.avatarUrl.let{view.loadAvatar(it)}
        }

        override fun getCount() = users.size
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        searchResultListPresenter.itemClickListener = { itemView ->
            val loginDisposable = Observable.just(searchResultListPresenter.users)
                .map { it[itemView.pos] }
                .subscribe(
                    { router.navigateTo(screens.userScreen(mapUserToGithubUser(it))) },
                    { error -> Log.d(TAG, "Error loading users $error") }
                )
            loginDisposable.dispose()
        }
    }

    private fun mapUserToGithubUser(user: User): GithubUser{
        return GithubUser(
            id = user.id.toString(),
            login = user.login,
            avatar_url = user.avatarUrl,
            repos_url = user.reposUrl
        )
    }

    fun backPressed(): Boolean{
        router.exit()
        disposable?.dispose()
        return true
    }

    fun searchUsers(query: String){
        viewState.showProgressBar()
        disposable = searchRepository.getSearchResults(query)
            .subscribeOn(Schedulers.io())
            .observeOn(uiScheduler)
            .doFinally {
                viewState.hideProgressBar()
            }
            .subscribe(
                {users ->
                    showResults(users)},
                {error ->
                    viewState.showError(error.message)}
            )
    }

    @VisibleForTesting
    private fun showResults(users: List<User>) {
        searchResultListPresenter.users.clear()
        searchResultListPresenter.users.addAll(users)
        viewState.showSearchResults(users)
    }

    override fun onDestroy() {
        searchScopeContainer.releaseSearchScope()
        super.onDestroy()
    }
}

