package data.repository

import data.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun getAllPosts(): Flow<List<Post>>
    fun getPost(id: String): Flow<Post>
}