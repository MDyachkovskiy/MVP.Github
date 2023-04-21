package gb.com.di.repository.module

import dagger.Module
import dagger.Provides
import gb.com.App
import gb.com.di.repository.IRepositoryScopeContainer
import gb.com.di.repository.RepositoryScope
import gb.com.mvp.model.api.IDataSource
import gb.com.mvp.model.entity.room.cache.IRoomGithubRepositoriesCache
import gb.com.mvp.model.entity.room.cache.RoomGithubRepositoriesCache
import gb.com.mvp.model.network.INetworkStatus
import gb.com.mvp.model.repository.userRepository.IGithubUserRepositories
import gb.com.mvp.model.repository.userRepository.RetrofitGithubUserRepository
import gb.com.mvp.model.room.Database

@Module
open class RepositoryModule {

    @Provides
    fun repositoriesCache(database: Database): IRoomGithubRepositoriesCache {
        return RoomGithubRepositoriesCache(database)
    }

    @RepositoryScope
    @Provides
    fun repositoriesRepo(
        api: IDataSource,
        networkStatus: INetworkStatus,
        roomCache: IRoomGithubRepositoriesCache
    ): IGithubUserRepositories
    {
        return RetrofitGithubUserRepository(api, networkStatus, roomCache)
    }

    @RepositoryScope
    @Provides
    open fun scopeContainer(app: App): IRepositoryScopeContainer = app
}