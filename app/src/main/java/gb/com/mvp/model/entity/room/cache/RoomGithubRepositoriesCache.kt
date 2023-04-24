package gb.com.mvp.model.entity.room.cache

import gb.com.mvp.model.entity.GithubUser
import gb.com.mvp.model.entity.GithubUserRepository
import gb.com.mvp.model.entity.room.RoomGithubUserRepository
import gb.com.mvp.model.room.Database
import io.reactivex.rxjava3.core.Single

class RoomGithubRepositoriesCache: IRoomGithubRepositoriesCache {
    override fun doUserRepositoriesCache(
        user: GithubUser,
        repositories: List<GithubUserRepository>,
        db: Database
    ): Single<List<GithubUserRepository>> {
        return Single.fromCallable {

            val roomUser = user.login?.let {
                db.userDao.findByLogin(it)
            } ?: throw java.lang.RuntimeException("No such in cache")

            val roomRepositories = repositories.map { repository ->
                RoomGithubUserRepository(
                    id = repository.id ?: "",
                    name = repository.name ?: "",
                    forksCount = repository.forks ?: 0,
                    userId = roomUser.id)
            }
            db.repositoryDao.insert(roomRepositories)
            return@fromCallable repositories
        }
    }
}