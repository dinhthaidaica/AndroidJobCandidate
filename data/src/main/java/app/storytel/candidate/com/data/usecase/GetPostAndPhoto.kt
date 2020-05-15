package app.storytel.candidate.com.data.usecase

import app.storytel.candidate.com.data.executor.PostExecutionThread
import app.storytel.candidate.com.data.executor.ThreadExecutor
import app.storytel.candidate.com.data.interactor.FlowableUseCase
import app.storytel.candidate.com.data.model.MyPost
import app.storytel.candidate.com.data.repository.AppRepository
import io.reactivex.Flowable
import io.reactivex.functions.BiFunction

class GetPostAndPhoto(
    private val repository: AppRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : FlowableUseCase<List<MyPost>, Void?>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Void?): Flowable<List<MyPost>> {
        val postFlowable = repository.getPosts()
        val photoFlowable = repository.getPhotos()
        return Flowable.zip(postFlowable, photoFlowable, BiFunction { posts, photos ->
            val listPost = mutableListOf<MyPost>()
            posts.forEach { postItem ->
                val myPost = MyPost()
                photos.forEach { photoItem ->
                    myPost.userId = postItem.userId
                    myPost.id = postItem.id
                    myPost.body = postItem.body
                    myPost.title = postItem.title
                    if (photoItem.id == postItem.id) {
                        myPost.photo = photoItem
                    }
                }
                listPost.add(myPost)
            }
            listPost
        })
    }

}