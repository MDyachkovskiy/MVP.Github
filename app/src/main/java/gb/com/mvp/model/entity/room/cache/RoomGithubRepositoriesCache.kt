package gb.com.mvp.model.entity.room.cache

import gb.com.mvp.model.entity.GithubUser
import gb.com.mvp.model.entity.GithubUserRepository
import gb.com.mvp.model.entity.room.RoomGithubUserRepository
import gb.com.mvp.model.room.Database
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RoomGithubRepositoriesCache(
    val db: Database
): IRoomGithubRepositoriesCache {

    override fun getUserRepositories(
        user: GithubUser
    ): Single<List<GithubUserRepository>> {
        return Single.fromCallable {

            val roomUser = user.login?.let {
                db.userDao.findByLogin(it)
            } ?: throw java.lang.RuntimeException("No such user in cache")

            return@fromCallable db.repositoryDao.findForUserRepositories(roomUser.id)
                .map{
                    GithubUserRepository(it.id, it.name, it.forksCount)
                }
        }.subscribeOn(Schedulers.io())
    }

    override fun putUserRepositories(user: GithubUser, repositories: List<GithubUserRepository>) =
        Completable.fromAction {
            val roomUser = db.userDao.findByLogin(user.login)
                ?: throw RuntimeException("No such user in cache")
            val roomRepository = repositories.map{
                RoomGithubUserRepository(
                    id = it.id ?: "",
                    name = it.name ?: "",
                    forksCount = it.forks ?: 0,
                    roomUser.id)
            }
            db.repositoryDao.insert(roomRepository)
        }.subscribeOn(Schedulers.io())
}