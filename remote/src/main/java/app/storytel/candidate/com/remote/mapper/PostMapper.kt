package app.storytel.candidate.com.remote.mapper

import app.storytel.candidate.com.data.model.Post
import app.storytel.candidate.com.remote.model.RemotePost

class PostMapper : RemoteMapper<RemotePost, Post> {
    override fun mapFromRemote(type: RemotePost): Post {
        return Post(
            type.userId,
            type.id,
            type.title,
            type.body
        )
    }

}