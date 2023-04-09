package gb.com.mvp.model.entity

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GithubUserRepositories (
    @Expose val id: String? = null,
    @Expose val name: String? = null,
    @Expose val forks: Int? = 0
): Parcelable