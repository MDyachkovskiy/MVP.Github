package gb.com.mvp.view.fragments.repositoryDetails

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface IRepositoryDetailsView: MvpView {
    fun setRepositoryForks()
}