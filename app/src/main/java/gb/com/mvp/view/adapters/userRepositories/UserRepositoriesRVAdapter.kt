package gb.com.mvp.view.adapters.userRepositories

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import gb.com.R
import gb.com.databinding.ItemUserRepositoryBinding
import gb.com.mvp.presenter.userRepositories.IUserRepositoryListPresenter

class UserRepositoriesRVAdapter (
    val presenter: IUserRepositoryListPresenter
    ): RecyclerView.Adapter<UserRepositoriesRVAdapter.ViewHolder>() {

        inner class ViewHolder(
            private val itemView: ItemUserRepositoryBinding
        ): RecyclerView.ViewHolder(itemView.root), IUserRepositoryItemView {

            override var pos: Int = -1


            override fun setId(id: String) {
                with (itemView){
                    val tvId: TextView = findViewById(R.id.tvId)
                    tvId.text = id
                }
            }

            override fun setName(name: String) {
                with (itemView){
                    val tvName: TextView = findViewById(R.id.tvName)
                    tvName.text = name
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemUserRepositoryBinding.inflate(LayoutInflater.from(parent.context),
        parent, false)).apply {
            itemView.setOnClickListener { presenter.itemClickListener?.invoke(this) }
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        presenter.bindView(holder.apply {
            pos = position
            itemView.setOnClickListener { presenter.itemClickListener?.invoke(holder) }
        })
    }

    override fun getItemCount() = presenter.getCount()

}