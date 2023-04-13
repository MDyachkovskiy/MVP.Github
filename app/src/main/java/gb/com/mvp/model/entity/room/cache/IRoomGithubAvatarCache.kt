package gb.com.mvp.model.entity.room.cache

import android.graphics.Bitmap
import gb.com.mvp.model.room.Database
import io.reactivex.rxjava3.core.Single

interface IRoomGithubAvatarCache {
    fun doAvatarCache(
        db: Database,
        bitmap: Bitmap?,
        url: String):  Single<String>

    fun getAvatarImageFromCache(db: Database, url: String): Single<String>
}