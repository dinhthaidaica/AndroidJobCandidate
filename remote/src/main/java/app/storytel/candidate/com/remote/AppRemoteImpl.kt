package app.storytel.candidate.com.remote

import app.storytel.candidate.com.data.model.Comment
import app.storytel.candidate.com.data.model.Photo
import app.storytel.candidate.com.data.model.Post
import app.storytel.candidate.com.data.source.AppDataStore
import app.storytel.candidate.com.remote.mapper.CommentMapper
import app.storytel.candidate.com.remote.mapper.PhotoMapper
import app.storytel.candidate.com.remote.mapper.PostMapper
import io.reactivex.Flowable
import io.reactivex.Single

class AppRemoteImpl constructor(
    private val endpoints: Endpoints,
    private val postMapper: PostMapper,
    private val photoMapper: PhotoMapper,
    private val commentMapper: CommentMapper
) : AppDataStore {

    override fun getPosts(): Flowable<List<Post>> {
        return endpoints.getPosts()
            .map {
                val posts = mutableListOf<Post>()
                it.forEach { item -> posts.add(postMapper.mapFromRemote(item)) }
                posts
            }
    }

    override fun getPhotos(): Flowable<List<Photo>> {
        return endpoints.getPhotos()
            .map {
                val photos = mutableListOf<Photo>()
                it.forEach { item -> photos.add(photoMapper.mapFromRemote(item)) }
                photos
            }
    }

    override fun getComments(postId: Int): Single<List<Comment>> {
        return endpoints.getComments(postId)
            .map {
                val comments = mutableListOf<Comment>()
                it.forEach { item -> comments.add(commentMapper.mapFromRemote(item)) }
                comments
            }
    }

}