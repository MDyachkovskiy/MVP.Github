package gb.com.mvp.model.repository

interface IImageLoader<T> {
    fun loadInto(url: String, container: T)
}