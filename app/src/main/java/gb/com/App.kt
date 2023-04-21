package gb.com

import android.app.Application
import gb.com.di.AppComponent
import gb.com.di.DaggerAppComponent
import gb.com.di.modules.AppModule
import gb.com.di.repository.IRepositoryScopeContainer
import gb.com.di.repository.RepositorySubcomponent
import gb.com.di.user.IUserScopeContainer
import gb.com.di.user.UserSubcomponent


class App: Application(), IUserScopeContainer, IRepositoryScopeContainer {

    companion object {
        lateinit var instance: App
    }

    lateinit var appComponent: AppComponent
        private set

    var userSubcomponent: UserSubcomponent? = null
        private set

    var repositorySubcomponent: RepositorySubcomponent? = null
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    fun initUserSubcomponent() = appComponent.usersSubcomponent().also{
       userSubcomponent = it
    }

    fun initRepositorySubcomponent() = userSubcomponent?.repositorySubcomponent().also{
        repositorySubcomponent = it
    }

    override fun releaseRepositoryScope() {
        repositorySubcomponent = null
    }

    override fun releaseUserScope() {
        userSubcomponent = null
    }
}