package gb.com.mvp.model.repository.users

import gb.com.mvp.model.api.IDataSource
import gb.com.mvp.model.entity.room.cache.IRoomGithubUsersCache
import gb.com.mvp.model.network.INetworkStatus
import io.reactivex.rxjava3.schedulers.Schedulers

class RetrofitGithubUsersRepo(
    private val api: IDataSource,
    private val networkStatus: INetworkStatus,
    private val roomCache: IRoomGithubUsersCache
): IGithubUsersRepo {

    override fun getUsers() = networkStatus.isOnlineSingle()
        .flatMap { isOnline ->
            if(isOnline) {
                api.getUsers()
                    .flatMap{users ->
                        roomCache.putUsers(users).toSingleDefault(users)
                    }
            } else {
                roomCache.getUsers()
            }
        }.subscribeOn(Schedulers.io())
}