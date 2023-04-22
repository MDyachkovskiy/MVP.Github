package gb.com.mvp.view.fragments.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import gb.com.App
import gb.com.databinding.FragmentSearchBinding
import gb.com.mvp.presenter.search.SearchPresenter
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
            App.instance.initSearchSubcomponent()?.inject(this)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentSearchBinding.inflate(inflater, container, false).also{
        _binding = it
    }.root

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun backPressed(): Boolean {
        presenter.backPressed()
        return true
    }
}