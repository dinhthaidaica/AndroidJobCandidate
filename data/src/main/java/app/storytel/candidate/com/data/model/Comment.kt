package app.storytel.candidate.com.data.model

data class Comment(
    val postId: Int,
    val id: Int,
    val name: String?,
    val email: String?,
    val body: String?
)