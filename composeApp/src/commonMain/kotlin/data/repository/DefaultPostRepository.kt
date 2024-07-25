package data.repository

import data.model.Post
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class DefaultPostRepository : PostRepository {
    private val posts = MutableStateFlow(List(16) {
        Post(
            id = it.toString(),
            title = "Title-#$it",
            description = "Description-#$it",
            author = "Author-#$it",
        )
    })

    override fun getAllPosts(): Flow<List<Post>> = posts

    override fun getPost(id: String): Post = posts.value.first { it.id == id }
}