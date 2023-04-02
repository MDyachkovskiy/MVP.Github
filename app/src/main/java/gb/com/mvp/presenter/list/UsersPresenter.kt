package gb.com.mvp.presenter.list

import android.util.Log
import com.github.terrakok.cicerone.Router
import gb.com.mvp.model.entity.GithubUser
import gb.com.mvp.model.entity.GithubUsersRepo
import gb.com.mvp.view.list.UsersView
import gb.com.mvp.view.list.IUserItemView
import gb.com.navigation.IScreens
import gb.com.utility.TAG
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter

class UsersPresenter(
    private val usersRepo: GithubUsersRepo,
    private val screens: IScreens,
    private val router: Router
): MvpPresenter<UsersView>() {

    class UserListPresenter: IUserListPresenter {
        val users = mutableListOf<GithubUser>()

        override var itemClickListener: ((IUserItemView) -> Unit)? = null

        override fun bindView(view: IUserItemView) {
            val user = users[view.pos]
            view.setLogin(user.login)
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
                .map{ it.login }
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
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { user -> usersListPresenter.users.add(user)},
                {error -> Log.d(TAG, "Error loading users $error")},
                {Log.d(TAG, "UsersPresenter Successfully Completed")
        viewState.updateList()})
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