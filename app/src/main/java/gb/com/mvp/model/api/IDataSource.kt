package gb.com.mvp.model.api

import gb.com.mvp.model.entity.GithubUser
import gb.com.mvp.model.entity.GithubUserRepository
import gb.com.mvp.model.entity.searchResponse.SearchResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface IDataSource {
    @GET("/users")
    fun getUsers(): Single<List<GithubUser>>

    @GET()
    fun getUserRepositories(@Url url: String): Single<List<GithubUserRepository>>

    @GET("/search/users")
    fun getSearchUserResult(
        @Query("q") query: String
    ): Single<SearchResponse>
}