package app.storytel.candidate.com.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.storytel.candidate.com.UiThread
import app.storytel.candidate.com.data.usecase.GetPostAndPhoto
import app.storytel.candidate.com.model.Resource
import app.storytel.candidate.com.model.ResourceState
import app.storytel.candidate.com.model.ui.Category
import app.storytel.candidate.com.model.ui.PostItem
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivityViewModel(
    private val getPostAndPhoto: GetPostAndPhoto
) : ViewModel() {

    private val categoryResource = Resource<List<Category>?>(ResourceState.LOADING, null, null)
    private val postAndPhotoLiveData: MutableLiveData<Resource<List<Category>?>> = MutableLiveData()
    private var disposable: Disposable? = null

    override fun onCleared() {
        disposable?.dispose()
        super.onCleared()
    }

    fun getPostsAndPhotos(): LiveData<Resource<List<Category>?>> {
        return postAndPhotoLiveData
    }

    fun fetchPostsAndPhotos() {
        postAndPhotoLiveData.postValue(categoryResource.loading())
        disposable = getPostAndPhoto.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .map {
                val listCategory = mutableListOf<Category>()
                val map = HashMap<Int, ArrayList<PostItem>>()
                it.forEach { myPost ->
                    if (map[myPost.userId] == null) {
                        map[myPost.userId] = ArrayList()
                    }

                    map[myPost.userId]!!.add(PostItem(myPost))
                }

                map.forEach { (userId, listPosts) ->
                    val category = Category(userId.toLong())
                    category.categoryName = "User $userId"
                    listPosts.forEach { item ->
                        item.header = category
                    }
                    category.items = listPosts
                    listCategory.add(category)
                }
                listCategory
            }
            .observeOn(UiThread().scheduler)
            .subscribe({
                postAndPhotoLiveData.postValue(categoryResource.success(it))
            }, {
                postAndPhotoLiveData.postValue(categoryResource.error(it.message ?: "", null))
            })
    }
}