package gb.com.mvp.model.entity.room.cache

import gb.com.mvp.model.entity.GithubUser
import gb.com.mvp.model.entity.GithubUserRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface IRoomGithubRepositoriesCache {

    fun getUserRepositories(
        user: GithubUser
    ): Single<List<GithubUserRepository>>

    fun putUserRepositories(
        user: GithubUser,
        repositories: List<GithubUserRepository>
    ): Completable
}