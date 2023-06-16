package gb.com.mvp.view.adapters.searchResult

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import gb.com.databinding.ItemUserBinding
import gb.com.mvp.model.repository.imageLoader.IImageLoader
import gb.com.mvp.presenter.search.ISearchResultListPresenter

class SearchResultRVAdapter(
    val presenter: ISearchResultListPresenter,
    val imageLoader: IImageLoader<ImageView>
): RecyclerView.Adapter<SearchResultRVAdapter.ViewHolder>() {

    inner class ViewHolder(
        private val vb: ItemUserBinding
    ): RecyclerView.ViewHolder(vb.root), ISearchResultItemView {

        override var pos = -1

        override fun setLogin(text: String) = with(vb) {
            tvLogin.text = text
        }

        override fun loadAvatar(url: String)  {
            imageLoader.loadInto(url, vb.ivAvatar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            .apply {
            itemView.setOnClickListener {
                presenter.itemClickListener?.invoke(this)
            }
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        presenter.bindView(holder.apply{
            pos = position
        })
    }

    override fun getItemCount() = presenter.getCount()

}