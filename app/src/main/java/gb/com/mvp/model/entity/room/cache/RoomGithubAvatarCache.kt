package gb.com.mvp.model.entity.room.cache

import android.graphics.Bitmap
import android.util.Log
import gb.com.mvp.model.entity.room.RoomImageCache
import gb.com.mvp.model.repository.avatar.IAvatarFile
import gb.com.mvp.model.room.Database
import gb.com.utility.TAG
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.File

class RoomGithubAvatarCache(
    private val repositoryFile: IAvatarFile
): IRoomGithubAvatarCache {

    override fun doAvatarCache(db: Database, bitmap: Bitmap?, url: String): Single<String> {
        return Single.fromCallable {
            repositoryFile.getDir()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .subscribe(
                    {
                        val subUrl = url.substringAfterLast("/")
                        val file = File(it, "/$subUrl.jpg")
                        repositoryFile.saveFile(file, bitmap)
                            .observeOn(Schedulers.io())
                            .subscribe(
                                {
                                    val cachedImage =
                                        RoomImageCache(url = url, localPath = file.absolutePath)
                                    db.imageCacheDao.insert(cachedImage)
                                },
                                { exception ->
                                    exception.message?.let { error ->
                                    Log.d(TAG, "Image cache error $error")
                                }}
                            )
                    },
                    {
                        it.message?.let {error ->
                            Log.d(TAG, "Image cache directory error $error")
                        }}
                )
            return@fromCallable url
        }
    }

    override fun getAvatarImageFromCache(db: Database, url: String): Single<String>{
        return Single.fromCallable {
            db.imageCacheDao.findByUrl(url).localPath
        }
    }
}