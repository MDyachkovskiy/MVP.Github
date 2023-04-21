package gb.com.di.user.module

import dagger.Module
import dagger.Provides
import gb.com.App
import gb.com.di.user.IUserScopeContainer
import gb.com.di.user.UserScope
import gb.com.mvp.model.api.IDataSource
import gb.com.mvp.model.entity.room.cache.IRoomGithubUsersCache
import gb.com.mvp.model.entity.room.cache.RoomGithubUsersCache
import gb.com.mvp.model.network.INetworkStatus
import gb.com.mvp.model.repository.users.IGithubUsersRepo
import gb.com.mvp.model.repository.users.RetrofitGithubUsersRepo
import gb.com.mvp.model.room.Database

@Module
class UserModule {

    @Provides
    fun usersCache(database: Database): IRoomGithubUsersCache{
        return RoomGithubUsersCache(database)
    }

    @UserScope
    @Provides
    open fun usersRepo(
        api: IDataSource,
        networkStatus: INetworkStatus,
        roomCache: IRoomGithubUsersCache
        ): IGithubUsersRepo{
        return RetrofitGithubUsersRepo(api, networkStatus, roomCache)
    }

    @UserScope
    @Provides
    open fun scopeContainer(app: App): IUserScopeContainer = app
}