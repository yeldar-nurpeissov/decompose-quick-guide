package presentation.list.model

import data.model.Post

data class ListState(
    val items: List<Post> = emptyList(),
)