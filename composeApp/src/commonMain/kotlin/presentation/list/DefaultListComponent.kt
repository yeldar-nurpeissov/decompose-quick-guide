package presentation.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import data.model.Post

internal class DefaultListComponent(
    componentContext: ComponentContext,
    private val listViewModelFactory: ListViewModel.Factory,
    private val postClicked: (postId: String) -> Unit,
    private val createNewPostClicked: () -> Unit,
) : ListComponent, ComponentContext by componentContext {

    private val viewModel = instanceKeeper.getOrCreate { listViewModelFactory() }

    override val state = viewModel.state

    override fun onPostClicked(post: Post) = postClicked(post.id)

    override fun fabClicked() = createNewPostClicked()

    class Factory(
        private val listViewModelFactory: ListViewModel.Factory,
    ) : ListComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
            postClicked: (postId: String) -> Unit,
            createNewPostClicked: () -> Unit,
        ): ListComponent {
            return DefaultListComponent(
                componentContext = componentContext,
                postClicked = postClicked,
                listViewModelFactory = listViewModelFactory,
                createNewPostClicked = createNewPostClicked,
            )
        }
    }
}
