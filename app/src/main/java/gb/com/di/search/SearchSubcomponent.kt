package gb.com.di.search

import dagger.Subcomponent
import gb.com.di.search.module.SearchModule
import gb.com.mvp.presenter.search.SearchPresenter

@SearchScope
@Subcomponent(
    modules = [
        SearchModule::class
    ]
)
interface SearchSubcomponent {

    fun inject (searchPresenter: SearchPresenter)

}