package gb.com.mvp.model.repository.imageLoader

interface IImageLoader<T> {
    fun loadInto(url: String, container: T)
}