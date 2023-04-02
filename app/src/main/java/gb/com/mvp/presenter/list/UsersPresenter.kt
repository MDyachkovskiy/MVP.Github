package gb.com.mvp.presenter.list

import android.util.Log
import com.github.terrakok.cicerone.Router
import gb.com.mvp.model.entity.GithubUser
import gb.com.mvp.model.entity.GithubUsersRepo
import gb.com.mvp.view.list.UsersView
import gb.com.mvp.view.list.IUserItemView
import gb.com.navigation.Screens
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import moxy.MvpPresenter

class UsersPresenter(
    private val usersRepo: GithubUsersRepo,
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

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        viewState.init()
        loadData()

        usersListPresenter.itemClickListener = {itemView ->
            val position = itemView.pos
            val login = usersListPresenter.users[position].login
            router.navigateTo(Screens.UserScreen(login))
        }
    }

    private fun loadData(){
        usersRepo.getUsers()
            .subscribe(UserObserver())
        viewState.updateList()
    }

    inner class UserObserver: Observer<GithubUser> {

        var disposable: Disposable? = null

        override fun onNext(user: GithubUser) {
            user.let { usersListPresenter.users.add(user) }
        }

        override fun onError(e: Throwable) {
            Log.d("@@@", "UsersPresenter error $e")
        }

        override fun onComplete() {
            Log.d("@@@", "UsersPresenter complete")
        }

        override fun onSubscribe(d: Disposable) {
            disposable = d
            Log.d("@@@", "UsersPresenter subscribe")
        }
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}