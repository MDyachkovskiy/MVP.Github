package gb.com.mvp.model.repository.userRepository

import gb.com.mvp.model.api.IDataSource
import gb.com.mvp.model.entity.GithubUser
import gb.com.mvp.model.entity.GithubUserRepository
import gb.com.mvp.model.entity.room.cache.IRoomGithubRepositoriesCache
import gb.com.mvp.model.network.INetworkStatus
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RetrofitGithubUserRepository (
    private val api: IDataSource,
    private val networkStatus: INetworkStatus,
    private val roomCache: IRoomGithubRepositoriesCache
    ): IGithubUserRepositories {

    override fun getUserRepositories(user: GithubUser) =
        networkStatus.isOnlineSingle().flatMap{isOnline ->
            if (isOnline) {
               user.repos_url?.let { url ->
                   api.getUserRepositories(url)
                       .flatMap { repositories ->
                           roomCache.putUserRepositories(user, repositories)
                               .toSingleDefault(repositories)
                       }
               } ?: Single.error<List<GithubUserRepository>>(RuntimeException
                   ("User has no repositories url"))
                    .subscribeOn(Schedulers.io())
            } else {
                roomCache.getUserRepositories(user)
                }
            }.subscribeOn(Schedulers.io())
        }