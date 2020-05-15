package app.storytel.candidate.com.remote.model

import com.google.gson.annotations.SerializedName

data class RemotePhoto(
    @SerializedName("albumId") val albumId: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String? = null,
    @SerializedName("url") val url: String? = null,
    @SerializedName("thumbnailUrl") val thumbnailUrl: String? = null
)