package app.storytel.candidate.com.data.source

import app.storytel.candidate.com.data.model.Comment
import app.storytel.candidate.com.data.model.Photo
import app.storytel.candidate.com.data.model.Post

interface AppDataStore {

    suspend fun getPosts(): List<Post>

    suspend fun getPhotos(): List<Photo>

    suspend fun getComments(postId: Int): List<Comment>
}