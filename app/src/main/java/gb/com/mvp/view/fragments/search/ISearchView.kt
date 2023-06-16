package gb.com.mvp.view.fragments.search

import gb.com.mvp.model.entity.searchResponse.User
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface ISearchView: MvpView {
    fun showSearchResults(users: List<User>)
    fun showEmptyResult()
    fun showError(error: String?)
    fun showProgressBar()
    fun hideProgressBar()
}