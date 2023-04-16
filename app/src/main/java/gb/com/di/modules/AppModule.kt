package gb.com.di.modules

import dagger.Module
import dagger.Provides
import gb.com.App

@Module
class AppModule(
    val app: App
) {

    @Provides
    fun app(): App = app

}