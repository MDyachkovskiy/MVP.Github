package gb.com.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import gb.com.mvp.model.entity.GithubUser
import gb.com.ui.fragments.UserFragment
import gb.com.ui.fragments.UsersFragment

class Screens : IScreens {

    override fun usersScreen() = FragmentScreen {
        UsersFragment.newInstance() }

    override fun userScreen(user: GithubUser) = FragmentScreen {
        UserFragment(user)
    }
}