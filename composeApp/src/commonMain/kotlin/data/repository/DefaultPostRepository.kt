package data.repository

import data.model.Post
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

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

    override fun getPost(
        id: String
    ): Flow<Post> = posts
        .map { it.first { post -> post.id == id } }
        .onEach { delay(500) }

    override suspend fun createPost(title: String, description: String, author: String) {
        delay(500)
        val post = Post(
            id = posts.value.size.toString(),
            title = title,
            description = description,
            author = author,
        )
        posts.update { it + post }
    }
}