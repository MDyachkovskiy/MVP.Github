package gb.com.mvp.model.entity.room.cache

import gb.com.mvp.model.entity.GithubUser
import gb.com.mvp.model.entity.room.RoomGithubUser
import gb.com.mvp.model.room.Database
import io.reactivex.rxjava3.core.Single

class RoomGithubUsersCache: IRoomGithubUsersCache {
    override fun doUserCache(
        githubUserList: List<GithubUser>,
        db: Database
    ): Single<List<GithubUser>> {
        return Single.fromCallable {
            val roomUsers = githubUserList.map {user ->
                RoomGithubUser(
                    id = user.id ?: "",
                    login = user.login ?: "",
                    avatarUrl = user.avatarUrl ?: "",
                    reposUrl = user.repos_url ?:  "")
            }
            db.userDao.insert(roomUsers)
            return@fromCallable githubUserList
        }
    }
}