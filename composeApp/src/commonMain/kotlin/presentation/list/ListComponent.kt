package presentation.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import data.model.Post

interface ListComponent {
    val model: Value<List<Post>>

    fun onPostClicked(post: Post)

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            postClicked: (postId: String) -> Unit,
        ): ListComponent
    }
}