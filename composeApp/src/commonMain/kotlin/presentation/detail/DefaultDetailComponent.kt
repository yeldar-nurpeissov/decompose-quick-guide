package presentation.detail

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import data.model.Post
import data.repository.PostRepository

internal class DefaultDetailComponent(
    componentContext: ComponentContext,
    postId: String,
    private val repository: PostRepository,
    private val onFinished: () -> Unit,
) : DetailComponent, ComponentContext by componentContext {

    override val model: Value<Post> = MutableValue(repository.getPost(postId))

    override fun onBackPressed() = onFinished()

    class Factory(
        private val repository: PostRepository,
    ) : DetailComponent.Factory {

        override fun invoke(
            componentContext: ComponentContext,
            postId: String,
            onFinished: () -> Unit,
        ): DetailComponent = DefaultDetailComponent(
            componentContext = componentContext,
            postId = postId,
            onFinished = onFinished,
            repository = repository,
        )
    }
}
