package gb.com.mvp.model.room

import androidx.room.RoomDatabase
import gb.com.mvp.model.entity.room.RoomGithubUser
import gb.com.mvp.model.entity.room.RoomGithubUserRepository
import gb.com.mvp.model.entity.room.RoomImageCache

@androidx.room.Database(entities = [RoomGithubUser::class,
RoomGithubUserRepository::class, RoomImageCache::class], version = 1)
abstract class Database: RoomDatabase() {

    abstract val userDao: UserDao
    abstract val repositoryDao: RepositoryDao
    abstract val imageCacheDao: ImageCacheDao

    companion object {
        const val DB_NAME = "database.db"
    }
}