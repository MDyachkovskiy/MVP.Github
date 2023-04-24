package gb.com.mvp.model.entity.room.cache

import gb.com.mvp.model.entity.GithubUser
import gb.com.mvp.model.entity.room.RoomGithubUser
import gb.com.mvp.model.room.Database
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RoomGithubUsersCache(
    val db: Database
): IRoomGithubUsersCache {
    override fun getUsers(): Single<List<GithubUser>> {
        return Single.fromCallable {
            db.userDao.getAll().map {user ->
                GithubUser(user.id, user.login, user.avatarUrl, user.reposUrl)
            }
        }
    }

    override fun putUsers (users: List<GithubUser>): Completable =
        Completable.fromAction {
        val roomUsers = users.map {user ->
            RoomGithubUser(
            id = user.id ?: "",
            login = user.login ?: "",
            avatarUrl = user.avatarUrl ?: "",
            reposUrl = user.repos_url ?:  ""
            )
        }
        db.userDao.insert(roomUsers)
    }.subscribeOn(Schedulers.io())
}