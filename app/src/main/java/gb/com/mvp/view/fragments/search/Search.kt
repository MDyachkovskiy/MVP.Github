package gb.com.mvp.view.fragments.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import gb.com.App
import gb.com.databinding.FragmentSearchBinding
import gb.com.mvp.model.entity.searchResponse.User
import gb.com.mvp.model.repository.imageLoader.GlideImageLoader
import gb.com.mvp.presenter.search.SearchPresenter
import gb.com.mvp.view.adapters.searchResult.SearchResultRVAdapter
import gb.com.mvp.view.main.BackButtonListener
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class Search: MvpAppCompatFragment(), ISearchView, BackButtonListener  {

    companion object {
        fun newInstance() = Search()
    }

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    val presenter: SearchPresenter by moxyPresenter {
        SearchPresenter().apply{
            App.instance.initSearchSubcomponent().inject(this)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        FragmentSearchBinding.inflate(inflater, container, false).also{
            _binding = it}

        with(binding){
            searchView.setOnQueryTextListener(
                object:  androidx.appcompat.widget.SearchView.OnQueryTextListener {

                    override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {presenter.searchUsers(it)}
                    return true
                }

                    override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun backPressed(): Boolean {
        presenter.backPressed()
        return true
    }

    override fun showSearchResults(users: List<User>){
        with(binding){
            if(emptyResult.visibility == View.VISIBLE){
                emptyResult.visibility = View.GONE
            }
            rvSearchResult.visibility = View.VISIBLE
            rvSearchResult.layoutManager = LinearLayoutManager(context)
            val adapter = SearchResultRVAdapter(presenter.searchResultListPresenter,
                GlideImageLoader().apply{
                App.instance.initSearchSubcomponent().inject(this)
            })
            rvSearchResult.adapter = adapter
        }
    }

    override fun showEmptyResult() {
        with(binding){
            if (rvSearchResult.visibility == View.VISIBLE) {
                rvSearchResult.visibility = View.GONE
                emptyResult.visibility = View.VISIBLE
            }
        }
    }

    override fun showError(error: String?) {
        Toast.makeText(context, "Unexpected error $error", Toast.LENGTH_LONG).show()
    }

    override fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }
}