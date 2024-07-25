package presentation.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import data.model.Post
import util.asValue

internal class DefaultListComponent(
    componentContext: ComponentContext,
    private val listStoreFactory: ListStoreFactory,
    private val postClicked: (postId: String) -> Unit,
    private val createNewPostClicked: () -> Unit,
) : ListComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { listStoreFactory.create() }

    override val model: Value<List<Post>> = store.asValue().map { it.items }

    override fun onPostClicked(post: Post) = postClicked(post.id)

    override fun fabClicked() = createNewPostClicked()

    class Factory(
        private val listStoreFactory: ListStoreFactory
    ) : ListComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
            postClicked: (postId: String) -> Unit,
            createNewPostClicked: () -> Unit,
        ): ListComponent {
            return DefaultListComponent(
                componentContext = componentContext,
                postClicked = postClicked,
                listStoreFactory = listStoreFactory,
                createNewPostClicked = createNewPostClicked,
            )
        }
    }
}
