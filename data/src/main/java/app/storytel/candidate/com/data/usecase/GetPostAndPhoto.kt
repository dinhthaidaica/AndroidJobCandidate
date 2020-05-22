package app.storytel.candidate.com.data.usecase

import app.storytel.candidate.com.data.model.MyPost
import app.storytel.candidate.com.data.repository.AppRepository

class GetPostAndPhoto(private val repository: AppRepository) {

    suspend fun getPostAndPhotos(): List<MyPost> {
        val listPosts = repository.getPosts()
        val listPhotos = repository.getPhotos()

        val listPost = mutableListOf<MyPost>()
        listPosts.forEach { postItem ->
            val myPost = MyPost()
            listPhotos.forEach { photoItem ->
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

        return listPost
    }

}