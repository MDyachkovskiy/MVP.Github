package gb.com.mvp.presenter.list

import android.util.Log
import com.github.terrakok.cicerone.Router
import gb.com.mvp.model.entity.GithubUser
import gb.com.mvp.model.repository.IGithubUsersRepo
import gb.com.mvp.view.list.IUserItemView
import gb.com.mvp.view.list.UsersView
import gb.com.navigation.IScreens
import gb.com.utility.TAG
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter

class UsersPresenter(
    private val uiScheduler: Scheduler,
    private val usersRepo: IGithubUsersRepo,
    private val screens: IScreens,
    private val router: Router
): MvpPresenter<UsersView>() {

    class UserListPresenter: IUserListPresenter {
        val users = mutableListOf<GithubUser>()

        override var itemClickListener: ((IUserItemView) -> Unit)? = null

        override fun bindView(view: IUserItemView) {
            val user = users[view.pos]
            user.login?.let {view.setLogin(it)}
            user.avatarUrl?.let {view.loadAvatar(it)}
        }

        override fun getCount() = users.size
    }

    val usersListPresenter = UserListPresenter()

    private var disposable: Disposable? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        viewState.init()
        loadData()

        usersListPresenter.itemClickListener = {itemView ->
            val loginDisposable = Observable.just(usersListPresenter.users)
                .map{ it[itemView.pos] }
                .subscribe(
                    {router.navigateTo(screens.userScreen(it))},
                    {error -> Log.d(TAG, "Error loading users $error")},
                    {Log.d(TAG, "UsersPresenter Successfully Completed")}
                )
            loginDisposable.dispose()
        }
    }

    private fun loadData(){
        disposable = usersRepo.getUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(uiScheduler)
            .subscribe({ repos ->
                usersListPresenter.users.clear()
                usersListPresenter.users.addAll(repos)
                viewState.updateList()
                       }, {
                    error -> Log.d(TAG, "Error loading users $error")})
    }

    fun backPressed(): Boolean {
        router.exit()
        disposable?.dispose()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        viewState.release()
    }
}