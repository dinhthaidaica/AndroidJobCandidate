package app.storytel.candidate.com.data.repository

import app.storytel.candidate.com.data.model.Comment
import app.storytel.candidate.com.data.model.Photo
import app.storytel.candidate.com.data.model.Post

interface AppRepository {

    suspend fun getPosts(): List<Post>

    suspend fun getPhotos(): List<Photo>

    suspend fun getComments(postId: Int): List<Comment>
}