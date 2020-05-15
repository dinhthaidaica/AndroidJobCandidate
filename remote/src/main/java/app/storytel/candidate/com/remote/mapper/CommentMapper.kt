package app.storytel.candidate.com.remote.mapper

import app.storytel.candidate.com.data.model.Comment
import app.storytel.candidate.com.remote.model.RemoteComment

class CommentMapper : RemoteMapper<RemoteComment, Comment> {
    override fun mapFromRemote(type: RemoteComment): Comment {
        return Comment(
            type.postId,
            type.id,
            type.name,
            type.email,
            type.body
        )
    }

}