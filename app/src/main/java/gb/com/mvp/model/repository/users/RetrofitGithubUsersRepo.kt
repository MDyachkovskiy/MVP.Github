package gb.com.mvp.model.repository.users

import gb.com.mvp.model.api.IDataSource
import gb.com.mvp.model.entity.GithubUser
import gb.com.mvp.model.entity.room.cache.RoomGithubUsersCache
import gb.com.mvp.model.network.INetworkStatus
import gb.com.mvp.model.room.Database
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RetrofitGithubUsersRepo(
    private val api: IDataSource,
    private val networkStatus: INetworkStatus,
    private val db: Database,
    private val roomCache: RoomGithubUsersCache
): IGithubUsersRepo {

    override fun getUsers() = networkStatus.isOnlineSingle()
        .flatMap { isOnline ->
            if(isOnline) {
                api.getUsers()
                    .flatMap{users ->
                        roomCache.doUserCache(users, db)
                    }
            } else {
                Single.fromCallable {
                    db.userDao.getAll().map{roomUser ->
                        GithubUser(roomUser.id, roomUser.login, roomUser.avatarUrl,roomUser.reposUrl)
                    }
                }
            }
        }.subscribeOn(Schedulers.io())
}