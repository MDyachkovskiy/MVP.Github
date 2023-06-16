package gb.com.di.modules

import android.widget.ImageView
import dagger.Module
import dagger.Provides
import gb.com.mvp.model.repository.imageLoader.GlideImageLoader
import gb.com.mvp.model.repository.imageLoader.IImageLoader
import javax.inject.Singleton

@Module
class ImageLoaderModule {
    @Singleton
    @Provides
    fun imageLoader(): IImageLoader<ImageView> = GlideImageLoader()
}