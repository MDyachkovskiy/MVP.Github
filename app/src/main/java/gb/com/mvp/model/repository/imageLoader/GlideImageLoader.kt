package gb.com.mvp.model.repository.imageLoader

import android.graphics.Bitmap
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import gb.com.mvp.model.entity.room.cache.IRoomGithubAvatarCache
import gb.com.mvp.model.network.INetworkStatus
import gb.com.mvp.model.room.Database
import gb.com.utility.TAG
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class GlideImageLoader: IImageLoader<ImageView> {

    @Inject
    lateinit var db: Database

    @Inject
    lateinit var avatarCache: IRoomGithubAvatarCache

    @Inject
    lateinit var networkStatus: INetworkStatus

    @Inject
    lateinit var uiScheduler: Scheduler

    override fun loadInto(url: String, container: ImageView) {

        var urlNew = url

        Glide.with(container.context)
            .asBitmap()
            .listener(object: RequestListener<Bitmap>{
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.d(TAG,"ImageLoader error ${e?.message}")
                    return true
                }

                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {

                   networkStatus.isOnlineSingle()
                        .flatMap { isOnline ->
                            if (isOnline) {
                                avatarCache.doAvatarCache(db, resource, url)
                            } else {
                                avatarCache.getAvatarImageFromCache(db, url)
                            }
                        }
                        .subscribeOn(Schedulers.io())
                        .observeOn(uiScheduler)
                        .subscribe(
                            {urlNew = it},
                            {error -> Log.d(TAG, "GlideImageLoader Error ${error.message}")}
                        )
                    return false
                }
            })
            .load(urlNew)
            .into(container)
    }
}
