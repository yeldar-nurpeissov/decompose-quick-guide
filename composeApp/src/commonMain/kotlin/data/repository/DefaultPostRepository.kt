package data.repository

import data.model.Post

class DefaultPostRepository : PostRepository {
    private val posts = List(16) {
        Post(
            id = it.toString(),
            title = "Title-#$it",
            description = "Description-#$it",
            author = "Author-#$it",
        )
    }

    override fun getAllPosts(): List<Post> = posts

    override fun getPost(id: String): Post = posts.first { it.id == id }
}