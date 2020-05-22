package app.storytel.candidate.com.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.storytel.candidate.com.data.model.MyPost
import app.storytel.candidate.com.data.usecase.GetPostAndPhoto
import app.storytel.candidate.com.model.Resource
import app.storytel.candidate.com.model.ResourceState
import app.storytel.candidate.com.model.ui.Category
import app.storytel.candidate.com.model.ui.PostItem
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
        viewModelScope.launch {
            try {
                val list = getPostAndPhoto.getPostAndPhotos()
                val listCategory = handleMappingData(list)

                postAndPhotoLiveData.postValue(categoryResource.success(listCategory))

            } catch (exception: Exception) {
                postAndPhotoLiveData.postValue(
                    categoryResource.error(
                        exception.message ?: "",
                        null
                    )
                )
            }
        }
    }

    private suspend fun handleMappingData(list: List<MyPost>): MutableList<Category> {
        return withContext(Dispatchers.IO) {
            val listCategory = mutableListOf<Category>()
            val map = HashMap<Int, ArrayList<PostItem>>()

            list.forEach { myPost ->
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
    }
}