package gb.com.di.modules

import dagger.Module
import dagger.Provides
import gb.com.App
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler

@Module
class AppModule(
    val app: App
) {

    @Provides
    fun app(): App = app

    @Provides
    fun uiScheduler(): Scheduler = AndroidSchedulers.mainThread()
}