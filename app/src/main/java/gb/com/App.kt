package gb.com

import android.app.Application
import gb.com.di.AppComponent
import gb.com.di.DaggerAppComponent
import gb.com.di.modules.AppModule


class App: Application() {

    companion object {
        lateinit var instance: App
    }

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}