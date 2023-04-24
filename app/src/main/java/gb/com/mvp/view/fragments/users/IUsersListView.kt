package gb.com.mvp.view.fragments.users

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface IUsersListView: MvpView {
    fun init()
    fun updateList()
}