package gb.com.di

import dagger.Component
import gb.com.di.modules.*
import gb.com.mvp.presenter.main.MainPresenter
import gb.com.mvp.presenter.users.UsersListPresenter
import gb.com.mvp.view.fragments.repositoryDetails.RepositoryDetailsFragment
import gb.com.mvp.view.fragments.userRepositories.UserRepositoriesFragment
import gb.com.mvp.view.fragments.users.UsersListFragment
import gb.com.mvp.view.main.MainActivity
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApiModule::class,
        AppModule::class,
        CacheModule::class,
        CiceroneModule::class,
        RepositoryModule::class
    ]
)


interface AppComponent {
    fun inject (mainActivity: MainActivity)
    fun inject (mainPresenter: MainPresenter)
    fun inject (usersPresenter: UsersListPresenter)

    fun inject (usersFragment: UsersListFragment)
    fun inject (userFragment: UserRepositoriesFragment)
    fun inject (repositoryFragment: RepositoryDetailsFragment)
}