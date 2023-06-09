package gb.com.mvp.view.adapters.users

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import gb.com.databinding.ItemUserBinding
import gb.com.mvp.model.repository.imageLoader.IImageLoader
import gb.com.mvp.presenter.users.IUsersListPresenter

class UsersRVAdapter(
    val presenter: IUsersListPresenter,
    val imageLoader: IImageLoader<ImageView>
): RecyclerView.Adapter<UsersRVAdapter.ViewHolder>() {

    inner class ViewHolder(
        private val vb: ItemUserBinding,
        ): RecyclerView.ViewHolder(vb.root), IUserItemView {

        override fun setLogin(text: String) = with(vb) {
           tvLogin.text = text
        }

        override fun loadAvatar(url: String) {
            imageLoader.loadInto(url, vb.ivAvatar)
        }

        override var pos = -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context),
        parent, false)).apply {
            itemView.setOnClickListener {
                presenter.itemClickListener?.invoke(this) }
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        presenter.bindView(holder.apply {
            pos = position
            itemView.setOnClickListener {
                presenter.itemClickListener?.invoke(holder)
            }
        })
    }

    override fun getItemCount(): Int {
        return presenter.getCount()
    }
}