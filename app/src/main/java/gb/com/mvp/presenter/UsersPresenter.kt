package gb.com.mvp.presenter

import com.github.terrakok.cicerone.Router
import gb.com.mvp.model.entity.GithubUser
import gb.com.mvp.model.entity.GithubUsersRepo
import gb.com.mvp.presenter.list.IUserListPresenter
import gb.com.mvp.view.UsersView
import gb.com.mvp.view.list.IUserItemView
import moxy.MvpPresenter

class UsersPresenter(
    val usersRepo: GithubUsersRepo,
    val router: Router
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
    }

    private fun loadData(){
        val users = usersRepo.getUsers()
        usersListPresenter.users.addAll(users)
        viewState.updateList()
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }

}