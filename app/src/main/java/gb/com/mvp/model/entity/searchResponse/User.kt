package gb.com.mvp.model.entity.searchResponse


import com.google.gson.annotations.SerializedName

data class User(
    val login: String = "",
    val id: Int = 0,
    @SerializedName("avatar_url")
    val avatarUrl: String = "",
    val url: String = "",
    @SerializedName("repos_url")
    val reposUrl: String = "",
)