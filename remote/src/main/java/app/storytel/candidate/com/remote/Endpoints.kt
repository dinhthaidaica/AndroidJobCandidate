package app.storytel.candidate.com.remote

import app.storytel.candidate.com.remote.model.RemoteComment
import app.storytel.candidate.com.remote.model.RemotePhoto
import app.storytel.candidate.com.remote.model.RemotePost
import retrofit2.http.GET
import retrofit2.http.Path

interface Endpoints {

    @GET("posts")
    suspend fun getPostsAsync(): List<RemotePost>

    @GET("photos")
    suspend fun getPhotosAsync(): List<RemotePhoto>

    @GET("posts/{id}/comments")
    suspend fun getCommentsAsync(@Path("id") postId: Int): List<RemoteComment>
}