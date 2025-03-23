package presentation.list

import com.arkivanov.decompose.ComponentContext
import data.model.Post
import kotlinx.coroutines.flow.StateFlow
import presentation.list.model.ListState

interface ListComponent {
    val state: StateFlow<ListState>

    fun onPostClicked(post: Post)
    fun fabClicked()

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            postClicked: (postId: String) -> Unit,
            createNewPostClicked: () -> Unit,
        ): ListComponent
    }
}