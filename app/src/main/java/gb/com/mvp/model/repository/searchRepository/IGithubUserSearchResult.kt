package gb.com.mvp.model.repository.searchRepository

import gb.com.mvp.model.entity.searchResponse.User
import io.reactivex.rxjava3.core.Single

interface IGithubUserSearchResult {
    fun getSearchResults(query: String): Single<List<User>>
}