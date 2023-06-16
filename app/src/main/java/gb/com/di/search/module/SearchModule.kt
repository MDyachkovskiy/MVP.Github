package gb.com.di.search.module

import dagger.Module
import dagger.Provides
import gb.com.App
import gb.com.di.search.ISearchScopeContainer
import gb.com.di.search.SearchScope
import gb.com.mvp.model.api.IDataSource
import gb.com.mvp.model.repository.searchRepository.IGithubUserSearchResult
import gb.com.mvp.model.repository.searchRepository.RetrofitGithubUserSearchResultRepository

@Module
class SearchModule {

    @SearchScope
    @Provides
    fun searchRepository(
        api: IDataSource
    ): IGithubUserSearchResult = RetrofitGithubUserSearchResultRepository(api)

    @SearchScope
    @Provides
    fun scopeContainer(app: App): ISearchScopeContainer = app

}