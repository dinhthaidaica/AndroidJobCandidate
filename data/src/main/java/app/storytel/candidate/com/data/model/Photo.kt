package app.storytel.candidate.com.data.model

data class Photo(
    val albumId: Int,
    val id: Int,
    val title: String? = null,
    val url: String? = null,
    val thumbnailUrl: String? = null
)