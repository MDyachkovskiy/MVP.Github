package gb.com.mvp.model.room

import androidx.room.*
import gb.com.mvp.model.entity.room.RoomGithubUserRepository

@Dao
interface RepositoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert (repository: RoomGithubUserRepository)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert (vararg repositories: RoomGithubUserRepository)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert (repositories: List<RoomGithubUserRepository>)

    @Update
    fun update (repository: RoomGithubUserRepository)

    @Update
    fun update (vararg repositories: RoomGithubUserRepository)

    @Update
    fun update (repositories: List<RoomGithubUserRepository>)

    @Delete
    fun delete (repository: RoomGithubUserRepository)

    @Delete
    fun delete (vararg repositories: RoomGithubUserRepository)

    @Delete
    fun delete (repositories: List<RoomGithubUserRepository>)

    @Query("SELECT * FROM RoomGithubUserRepository")
    fun getAll(): List<RoomGithubUserRepository>

    @Query("SELECT * FROM RoomGithubUserRepository WHERE userId = :userId")
    fun findForUserRepositories(userId: String): List<RoomGithubUserRepository>
}