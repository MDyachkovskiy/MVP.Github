package gb.com.mvp.model.room

import androidx.room.*
import gb.com.mvp.model.entity.room.RoomImageCache

@Dao
interface ImageCacheDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(image: RoomImageCache)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg images: RoomImageCache)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(images: List<RoomImageCache>)

    @Update
    fun update(image: RoomImageCache)

    @Update
    fun update(vararg images: RoomImageCache)

    @Update
    fun update(images: List<RoomImageCache>)

    @Delete
    fun delete(image: RoomImageCache)

    @Delete
    fun delete(vararg images: RoomImageCache)

    @Delete
    fun delete(images: List<RoomImageCache>)

    @Query("SELECT * FROM RoomImageCache")
    fun getAll(): List<RoomImageCache>

    @Query("SELECT * FROM RoomImageCache WHERE url = :url")
    fun findByUrl(url: String): RoomImageCache
}