package app.storytel.candidate.com.remote

import app.storytel.candidate.com.remote.model.RemoteComment
import app.storytel.candidate.com.remote.model.RemotePhoto
import app.storytel.candidate.com.remote.model.RemotePost
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface Endpoints {

    @GET("posts")
    fun getPosts(): Flowable<List<RemotePost>>

    @GET("photos")
    fun getPhotos(): Flowable<List<RemotePhoto>>

    @GET("posts/{id}/comments")
    fun getComments(@Path("id") postId: Int): Single<List<RemoteComment>>
}