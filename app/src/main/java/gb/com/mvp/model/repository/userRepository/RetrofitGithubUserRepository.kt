package gb.com.mvp.model.repository.userRepository

import gb.com.mvp.model.api.IDataSource
import gb.com.mvp.model.entity.GithubUser
import gb.com.mvp.model.entity.GithubUserRepository
import gb.com.mvp.model.entity.room.RoomGithubUserRepository
import gb.com.mvp.model.network.INetworkStatus
import gb.com.mvp.model.room.Database
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RetrofitGithubUserRepository (
    private val api: IDataSource,
    private val networkStatus: INetworkStatus,
    private val db: Database
    ): IGithubUserRepositories {

    override fun getUserRepositories(user: GithubUser) =
        networkStatus.isOnlineSingle().flatMap{isOnline ->
            if (isOnline) {
               user.repos_url?.let { url ->
                   api.getUserRepositories(url)
                       .flatMap { repositories ->
                           Single.fromCallable {
                               val roomUser = user.login?.let {
                                   db.userDao.findByLogin(it)
                               } ?: throw RuntimeException("No such user in cache")
                               val roomUserRepositories = repositories.map {repository ->
                                   RoomGithubUserRepository(
                                       repository.id ?: "",
                                       repository.name ?: "",
                                       repository.forks ?: 0,
                                       roomUser.id) }
                               db.repositoryDao.insert(roomUserRepositories)
                               repositories
                           }
                       }
               } ?: Single.error<List<GithubUserRepository>>(RuntimeException
                   ("User has no repositories url"))
                    .subscribeOn(Schedulers.io())

            } else {
                Single.fromCallable {
                    val roomUser = user.login?.let{
                        db.userDao.findByLogin(it)} ?: throw RuntimeException("No such user in cache")
                        db.repositoryDao.findForUserRepositories(roomUser.id)
                            .map{
                                GithubUserRepository(it.id, it.name, it.forksCount)
                            }
                    }
                }.subscribeOn(Schedulers.io())
            }
        }