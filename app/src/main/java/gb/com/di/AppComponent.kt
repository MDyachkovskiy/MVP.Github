package gb.com.di

import dagger.Component
import gb.com.di.modules.*
import gb.com.di.search.SearchSubcomponent
import gb.com.di.user.UserSubcomponent
import gb.com.mvp.presenter.main.MainPresenter
import gb.com.mvp.view.main.MainActivity
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApiModule::class,
        AppModule::class,
        DatabaseModule::class,
        CiceroneModule::class,
        AvatarCacheModule::class,
        ImageLoaderModule::class
    ]
)


interface AppComponent {
    fun usersSubcomponent(): UserSubcomponent
    fun searchSubcomponent(): SearchSubcomponent

    fun inject (mainActivity: MainActivity)
    fun inject (mainPresenter: MainPresenter)
}