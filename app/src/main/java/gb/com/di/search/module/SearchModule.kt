package gb.com.di.search.module

import dagger.Module
import dagger.Provides
import gb.com.App
import gb.com.di.search.ISearchScopeContainer
import gb.com.di.search.SearchScope

@Module
class SearchModule {

    @SearchScope
    @Provides
    fun scopeContainer(app: App): ISearchScopeContainer = app

}