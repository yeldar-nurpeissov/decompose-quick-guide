package data.repository

import data.model.Post

interface PostRepository {
    fun getAllPosts(): List<Post>
    fun getPost(id: String): Post
}