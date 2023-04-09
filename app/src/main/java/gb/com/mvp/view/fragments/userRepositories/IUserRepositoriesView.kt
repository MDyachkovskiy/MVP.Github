package gb.com.mvp.view.fragments.userRepositories

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface IUserRepositoriesView: MvpView {
    fun init()
    fun updateList()
    fun hideProgressBar()
}