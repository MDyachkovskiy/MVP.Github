package gb.com.di.modules

import dagger.Module
import dagger.Provides
import gb.com.mvp.model.api.IDataSource
import gb.com.mvp.model.entity.room.cache.IRoomGithubRepositoriesCache
import gb.com.mvp.model.entity.room.cache.IRoomGithubUsersCache
import gb.com.mvp.model.network.INetworkStatus
import gb.com.mvp.model.repository.userRepository.IGithubUserRepositories
import gb.com.mvp.model.repository.userRepository.RetrofitGithubUserRepository
import gb.com.mvp.model.repository.users.IGithubUsersRepo
import gb.com.mvp.model.repository.users.RetrofitGithubUsersRepo
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun usersRepository(
        api: IDataSource,
        networkStatus: INetworkStatus,
        roomCache: IRoomGithubUsersCache
    ): IGithubUsersRepo = RetrofitGithubUsersRepo(api, networkStatus, roomCache)

    @Singleton
    @Provides
    fun userRepoRepository(
        api: IDataSource,
        networkStatus: INetworkStatus,
        roomCache: IRoomGithubRepositoriesCache
    ): IGithubUserRepositories = RetrofitGithubUserRepository(api, networkStatus, roomCache)

}