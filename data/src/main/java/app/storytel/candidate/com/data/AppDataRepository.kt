package app.storytel.candidate.com.data

import app.storytel.candidate.com.data.model.Comment
import app.storytel.candidate.com.data.model.Photo
import app.storytel.candidate.com.data.model.Post
import app.storytel.candidate.com.data.repository.AppRepository
import app.storytel.candidate.com.data.source.AppDataStoreFactory

class AppDataRepository(private val storeFactory: AppDataStoreFactory) : AppRepository {

    override suspend fun getPosts(): List<Post> {
        return storeFactory.retrieveRemoteDataStore().getPosts()
    }

    override suspend fun getPhotos(): List<Photo> {
        return storeFactory.retrieveRemoteDataStore().getPhotos()
    }

    override suspend fun getComments(postId: Int): List<Comment> {
        return storeFactory.retrieveRemoteDataStore().getComments(postId)
    }

}