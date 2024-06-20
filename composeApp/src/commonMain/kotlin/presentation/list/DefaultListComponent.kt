package presentation.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import model.Post

class DefaultListComponent(
    componentContext: ComponentContext,
    private val postClicked: (Post) -> Unit,
) : ListComponent, ComponentContext by componentContext {
    override val model: Value<List<Post>> = MutableValue(
        (0..16).map {
            Post(
                id = it.toString(),
                title = "Title-#$it",
                description = "Description-#$it",
                author = "Author-#$it",
            )
        }
    )

    override fun onPostClicked(post: Post) = postClicked(post)
}
