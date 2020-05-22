package app.storytel.candidate.com.data.usecase

import app.storytel.candidate.com.data.model.Comment
import app.storytel.candidate.com.data.repository.AppRepository

class GetComments(private val repository: AppRepository) {
    suspend fun getComments(postId: Int): List<Comment> {
        return repository.getComments(postId)
    }
}