package app.storytel.candidate.com.data

import app.storytel.candidate.com.data.model.Comment
import app.storytel.candidate.com.data.model.Photo
import app.storytel.candidate.com.data.model.Post
import app.storytel.candidate.com.data.repository.AppRepository
import app.storytel.candidate.com.data.source.AppDataStoreFactory
import io.reactivex.Flowable
import io.reactivex.Single

class AppDataRepository(private val storeFactory: AppDataStoreFactory) : AppRepository {

    override fun getPosts(): Flowable<List<Post>> {
        return storeFactory.retrieveRemoteDataStore().getPosts()
    }

    override fun getPhotos(): Flowable<List<Photo>> {
        return storeFactory.retrieveRemoteDataStore().getPhotos()
    }

    override fun getComments(postId: Int): Single<List<Comment>> {
        return storeFactory.retrieveRemoteDataStore().getComments(postId)
    }

}