package app.storytel.candidate.com.remote

import app.storytel.candidate.com.data.model.Comment
import app.storytel.candidate.com.data.model.Photo
import app.storytel.candidate.com.data.model.Post
import app.storytel.candidate.com.data.source.AppDataStore
import app.storytel.candidate.com.remote.mapper.CommentMapper
import app.storytel.candidate.com.remote.mapper.PhotoMapper
import app.storytel.candidate.com.remote.mapper.PostMapper

class AppRemoteImpl constructor(
    private val endpoints: Endpoints,
    private val postMapper: PostMapper,
    private val photoMapper: PhotoMapper,
    private val commentMapper: CommentMapper
) : AppDataStore {

    override suspend fun getPosts(): List<Post> {
        val list = endpoints.getPostsAsync()
        val result = mutableListOf<Post>()
        list.forEach { item -> result.add(postMapper.mapFromRemote(item)) }
        return result
    }

    override suspend fun getPhotos(): List<Photo> {
        val list = endpoints.getPhotosAsync()
        val result = mutableListOf<Photo>()
        list.forEach { item -> result.add(photoMapper.mapFromRemote(item)) }
        return result
    }

    override suspend fun getComments(postId: Int): List<Comment> {
        val list = endpoints.getCommentsAsync(postId)
        val comments = mutableListOf<Comment>()
        list.forEach { item -> comments.add(commentMapper.mapFromRemote(item)) }
        return comments
    }

}