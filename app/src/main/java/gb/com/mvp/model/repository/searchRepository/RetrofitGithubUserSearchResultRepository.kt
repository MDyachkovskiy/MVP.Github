package gb.com.mvp.model.repository.searchRepository

import gb.com.mvp.model.api.IDataSource
import gb.com.mvp.model.entity.searchResponse.User
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

open class RetrofitGithubUserSearchResultRepository(
    private val api: IDataSource
): IGithubUserSearchResult{

    override fun getSearchResults(query: String): Single<List<User>> {
        return api.getSearchUserResult(query)
            .map{it.items}
            .onErrorResumeNext { error: Throwable ->
                Single.error(Exception("Возникла ошибка при обработке результатов поиска ${error.message}"))
            }
            .subscribeOn(Schedulers.io())
    }
}