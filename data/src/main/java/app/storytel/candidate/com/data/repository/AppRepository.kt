package app.storytel.candidate.com.data.repository

import app.storytel.candidate.com.data.model.Comment
import app.storytel.candidate.com.data.model.Photo
import app.storytel.candidate.com.data.model.Post
import io.reactivex.Flowable
import io.reactivex.Single

interface AppRepository {

    fun getPosts(): Flowable<List<Post>>

    fun getPhotos(): Flowable<List<Photo>>

    fun getComments(postId: Int): Single<List<Comment>>
}