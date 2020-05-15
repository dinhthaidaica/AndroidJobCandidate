package app.storytel.candidate.com.data.interactor

import app.storytel.candidate.com.data.executor.PostExecutionThread
import app.storytel.candidate.com.data.executor.ThreadExecutor
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

abstract class SingleUseCase<T, in Params> constructor(
    private val threadExecutor: ThreadExecutor,
    private val postExecutionThread: PostExecutionThread
) {
    protected abstract fun buildUseCaseObservable(params: Params? = null): Single<T>

    open fun execute(params: Params? = null): Single<T> {
        return this.buildUseCaseObservable(params)
            .subscribeOn(Schedulers.from(threadExecutor))
            .observeOn(postExecutionThread.scheduler)
    }
}