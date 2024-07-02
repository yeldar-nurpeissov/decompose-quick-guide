package presentation.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import data.model.Post
import data.repository.PostRepository

class DefaultListComponent(
    componentContext: ComponentContext,
    repository: PostRepository,
    private val postClicked: (postId: String) -> Unit,
    private val createNewPostClicked:() -> Unit,
) : ListComponent, ComponentContext by componentContext {

    override val model: Value<List<Post>> = MutableValue(repository.getAllPosts())

    override fun onPostClicked(post: Post) = postClicked(post.id)

    override fun fabClicked() = createNewPostClicked()

    class Factory(
        private val repository: PostRepository
    ) : ListComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
            postClicked: (postId: String) -> Unit,
            createNewPostClicked: () -> Unit,
        ): ListComponent {
            return DefaultListComponent(
                componentContext = componentContext,
                postClicked = postClicked,
                repository = repository,
                createNewPostClicked = createNewPostClicked,
            )
        }
    }
}
