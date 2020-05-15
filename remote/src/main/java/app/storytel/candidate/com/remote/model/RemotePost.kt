package app.storytel.candidate.com.remote.model

import com.google.gson.annotations.SerializedName

data class RemotePost(
    @SerializedName("userId") val userId: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String?,
    @SerializedName("body") val body: String?
)