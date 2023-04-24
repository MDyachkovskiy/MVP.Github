package gb.com.mvp.model.entity.room.cache

import gb.com.mvp.model.entity.GithubUser
import gb.com.mvp.model.entity.GithubUserRepository
import gb.com.mvp.model.room.Database
import io.reactivex.rxjava3.core.Single

interface IRoomGithubRepositoriesCache {
    fun doUserRepositoriesCache(
        user: GithubUser,
        repositories: List<GithubUserRepository>,
        db: Database
    ): Single<List<GithubUserRepository>>
}