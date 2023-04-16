package gb.com.di.modules

import dagger.Module
import dagger.Provides
import gb.com.mvp.model.api.IDataSource
import gb.com.mvp.model.entity.room.cache.IRoomGithubUsersCache
import gb.com.mvp.model.network.INetworkStatus
import gb.com.mvp.model.repository.users.IGithubUsersRepo
import gb.com.mvp.model.repository.users.RetrofitGithubUsersRepo
import gb.com.mvp.model.room.Database

@Module
class RepositoryModule {

    @Provides
    fun usersRepository(
        api: IDataSource,
        networkStatus: INetworkStatus,
        db: Database,
        roomCache: IRoomGithubUsersCache
    ): IGithubUsersRepo = RetrofitGithubUsersRepo(api, networkStatus, roomCache)

}