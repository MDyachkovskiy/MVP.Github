package gb.com.mvp.presenter.userRepositories

import android.util.Log
import com.github.terrakok.cicerone.Router
import gb.com.di.repository.IRepositoryScopeContainer
import gb.com.mvp.model.entity.GithubUser
import gb.com.mvp.model.entity.GithubUserRepository
import gb.com.mvp.model.repository.userRepository.IGithubUserRepositories
import gb.com.mvp.view.adapters.userRepositories.IUserRepositoryItemView
import gb.com.mvp.view.fragments.userRepositories.IUserRepositoriesView
import gb.com.navigation.IScreens
import gb.com.utility.TAG
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import moxy.MvpPresenter
import javax.inject.Inject

class UserRepositoryListPresenter(
    private val user: GithubUser
) : MvpPresenter<IUserRepositoriesView>(){

    @Inject lateinit var uiScheduler: Scheduler
    @Inject lateinit var usersRepo: IGithubUserRepositories
    @Inject lateinit var router: Router
    @Inject lateinit var screens: IScreens
    @Inject lateinit var repositoryScopeContainer: IRepositoryScopeContainer

    class UserRepositoriesListPresenter: IUserRepositoryListPresenter{

        val repositories = mutableListOf<GithubUserRepository>()

        override var itemClickListener: ((IUserRepositoryItemView) -> Unit)? = null

        override fun bindView(view: IUserRepositoryItemView) {
            val repository = repositories[view.pos]
            repository.id?.let { view.setId(it)}
            repository.name?.let {view.setName(it)}
        }

        override fun getCount(): Int = repositories.size

    }

    val userRepositoryListPresenter = UserRepositoriesListPresenter()

    private var disposable: Disposable? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        viewState.init()
        loadRepositories()

        userRepositoryListPresenter.itemClickListener = { itemView ->
            val repositoryDetailsDisposable =
                Observable.just(userRepositoryListPresenter.repositories)
                .map{it[itemView.pos]}
                    .subscribe(
                        {router.navigateTo(screens.repositoryDetailsScreen(user, it))},
                        {error -> Log.d(TAG, "Error loading repo details $error")}
                    )
            repositoryDetailsDisposable.dispose()
            }
        }

    private fun loadRepositories() {
        user.let { user ->
            disposable = usersRepo.getUserRepositories(user)
                .observeOn(uiScheduler)
                .subscribe({
                    userRepositoryListPresenter.repositories.clear()
                    userRepositoryListPresenter.repositories.addAll(it)
                    viewState.updateList()
                    viewState.hideProgressBar()
                }, {
                    Log.d(TAG, "Error in LoadRepositories function $it")
                }
                )
        }
    }

    fun backPressed(): Boolean {
        router.backTo(screens.usersScreen())
        disposable?.dispose()
        return true
    }

    override fun onDestroy() {
        repositoryScopeContainer.releaseRepositoryScope()
        super.onDestroy()
    }
}