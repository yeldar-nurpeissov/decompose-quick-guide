package presentation.list

import com.arkivanov.decompose.value.Value
import model.Post

interface ListComponent {
    val model: Value<List<Post>>

    fun onPostClicked(post: Post)
}