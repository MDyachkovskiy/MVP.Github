package gb.com

import gb.com.mvp.model.entity.searchResponse.User
import gb.com.mvp.model.repository.searchRepository.RetrofitGithubUserSearchResultRepository
import gb.com.mvp.presenter.search.SearchPresenter
import gb.com.mvp.view.fragments.search.ISearchView
import gb.com.mvp.view.fragments.search.`ISearchView$$State`
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SearchPresenterTest {

    private lateinit var presenter: SearchPresenter

    @Mock
    private lateinit var repository: RetrofitGithubUserSearchResultRepository

    @Mock
    private lateinit var view: ISearchView

    @Mock
    private lateinit var viewState: `ISearchView$$State`

    @Before
    fun setUp() {

        presenter = SearchPresenter()
        presenter.attachView(view)
        presenter.setViewState(viewState)
        presenter.searchRepository = repository
        presenter.uiScheduler = Schedulers.trampoline()

    }

    @After
    fun cleanUp() {
        presenter.detachView(view)
    }

    @Test
    fun testSearchUsers_Success() {
        val searchQuery = "some query"
        val users = listOf<User>()


        `when` (repository.getSearchResults(searchQuery))
            .thenReturn(Single.just(users))

        presenter.searchUsers(searchQuery)

        verify(repository, Mockito.times(1))
            .getSearchResults(searchQuery)
        verify(viewState).showProgressBar()
        verify(repository).getSearchResults(searchQuery)
        verify(viewState).hideProgressBar()
        verify(viewState).showSearchResults(users)
    }

    @Test
    fun testSearchUsers_Error() {
        val query = "testQuery"
        val exception = Exception("Test Exception")

        `when`(repository.getSearchResults(query)).thenReturn(Single.error(exception))

        presenter.searchUsers(query)

        verify(viewState).showProgressBar()
        verify(repository).getSearchResults(query)
        verify(viewState).hideProgressBar()
        verify(viewState).showError(exception.message)
    }
}