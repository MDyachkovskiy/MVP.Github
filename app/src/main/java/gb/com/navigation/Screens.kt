package gb.com.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import gb.com.mvp.model.entity.GithubUser
import gb.com.mvp.model.entity.GithubUserRepository
import gb.com.mvp.view.fragments.repositoryDetails.RepositoryDetailsFragment
import gb.com.mvp.view.fragments.search.Search
import gb.com.mvp.view.fragments.userRepositories.UserRepositoriesFragment
import gb.com.mvp.view.fragments.users.UsersListFragment

class Screens : IScreens {

    override fun usersScreen() = FragmentScreen {
        UsersListFragment.newInstance() }

    override fun userScreen(user: GithubUser) = FragmentScreen {
        UserRepositoriesFragment.newInstance(user)
    }

    override fun repositoryDetailsScreen(user: GithubUser, userRepo: GithubUserRepository) =
        FragmentScreen { RepositoryDetailsFragment.newInstance(user, userRepo)
    }

    override fun searchScreen() = FragmentScreen {
        Search.newInstance()
    }
}