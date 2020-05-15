package app.storytel.candidate.com.ui.comment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.storytel.candidate.com.UiThread
import app.storytel.candidate.com.data.usecase.GetComments
import app.storytel.candidate.com.model.Resource
import app.storytel.candidate.com.model.ResourceState
import app.storytel.candidate.com.model.ui.CommentItem
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class DetailActivityViewModel(
    private val getComments: GetComments
) : ViewModel() {

    private val commentResource = Resource(ResourceState.LOADING, null, null)
    private val commentLiveData: MutableLiveData<Resource<List<CommentItem>?>> = MutableLiveData()
    private var disposable: Disposable? = null

    override fun onCleared() {
        disposable?.dispose()
        super.onCleared()
    }

    fun getComments(): LiveData<Resource<List<CommentItem>?>> {
        return commentLiveData
    }

    fun fetchComments(postId: Int) {
        commentLiveData.postValue(commentResource.loading())
        disposable = getComments.execute(GetComments.Params(postId))
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .map {
                it.sortedByDescending { it.id }
                val comments = mutableListOf<CommentItem>()
                if (it.size < 3) {
                    it.forEach { item -> comments.add(CommentItem(item)) }
                } else {
                    for ((index, item) in it.withIndex()) {
                        if (index < 3) {
                            comments.add(CommentItem(item))
                        }
                    }
                }

                comments
            }
            .observeOn(UiThread().scheduler)
            .subscribe({
                commentLiveData.postValue(commentResource.success(it))
            }, {
                commentLiveData.postValue(commentResource.error(it.message ?: "", null))
            })
    }
}