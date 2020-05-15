package app.storytel.candidate.com.data.usecase

import app.storytel.candidate.com.data.executor.PostExecutionThread
import app.storytel.candidate.com.data.executor.ThreadExecutor
import app.storytel.candidate.com.data.interactor.SingleUseCase
import app.storytel.candidate.com.data.model.Comment
import app.storytel.candidate.com.data.repository.AppRepository
import io.reactivex.Single

class GetComments(
    private val repository: AppRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<List<Comment>, GetComments.Params>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Params?): Single<List<Comment>> {
        return repository.getComments(params!!.postId)
    }

    data class Params(val postId: Int)
}