package data.repository

import data.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun getAllPosts(): Flow<List<Post>>
    fun getPost(id: String): Flow<Post>

    suspend fun createPost(
        title: String,
        description: String,
        author: String,
    )
}