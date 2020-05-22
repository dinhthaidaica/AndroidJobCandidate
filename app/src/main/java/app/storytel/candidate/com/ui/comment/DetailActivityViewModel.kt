package app.storytel.candidate.com.ui.comment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.storytel.candidate.com.data.model.Comment
import app.storytel.candidate.com.data.usecase.GetComments
import app.storytel.candidate.com.model.Resource
import app.storytel.candidate.com.model.ResourceState
import app.storytel.candidate.com.model.ui.CommentItem
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
        viewModelScope.launch {
            try {
                val list = getComments.getComments(postId)
                val sortedList = list.sortedByDescending { it.id }
                val comments = handleDataMapping(sortedList)

                commentLiveData.postValue(commentResource.success(comments))
            } catch (exception: Exception) {
                commentLiveData.postValue(commentResource.error(exception.message ?: "", null))
            }
        }
    }

    private suspend fun handleDataMapping(sortedList: List<Comment>): MutableList<CommentItem> {
        return withContext(Dispatchers.IO) {
            val comments = mutableListOf<CommentItem>()
            if (sortedList.size < 3) {
                sortedList.forEach { item -> comments.add(CommentItem(item)) }
            } else {
                for ((index, item) in sortedList.withIndex()) {
                    if (index < 3) {
                        comments.add(CommentItem(item))
                    }
                }
            }
            comments
        }
    }
}