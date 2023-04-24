package gb.com.mvp.model.entity.room.cache

import gb.com.mvp.model.entity.GithubUser
import gb.com.mvp.model.room.Database
import io.reactivex.rxjava3.core.Single

interface IRoomGithubUsersCache {
    fun doUserCache(
        githubUserList: List<GithubUser>,
        db: Database
    ): Single<List<GithubUser>>
}

