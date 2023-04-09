package gb.com.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import gb.com.mvp.model.entity.GithubUser
import gb.com.mvp.model.entity.GithubUserRepositories
import gb.com.mvp.view.fragments.repositoryDetails.RepositoryDetailsFragment
import gb.com.mvp.view.fragments.userRepositories.UserRepositoriesFragment
import gb.com.mvp.view.fragments.users.IUsersListFragment

class Screens : IScreens {

    override fun usersScreen() = FragmentScreen {
        IUsersListFragment.newInstance() }

    override fun userScreen(user: GithubUser) = FragmentScreen {
        UserRepositoriesFragment(user)
    }

    override fun repositoryDetailsScreen(user: GithubUser, userRepo: GithubUserRepositories) =
        FragmentScreen { RepositoryDetailsFragment.newInstance(user, userRepo)
    }
}