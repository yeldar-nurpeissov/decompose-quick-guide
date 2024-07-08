package presentation.detail

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import data.model.Post
import data.repository.PostRepository

internal class DefaultDetail2Component(
    componentContext: ComponentContext,
    private val postId: String,
    private val repository: PostRepository,
    private val navigateToDetail: (String) -> Unit,
    private val onFinished: () -> Unit,
) : Detail2Component, ComponentContext by componentContext {

    override val model: Value<Post> = MutableValue(repository.getPost(postId))

    override fun onBackPressed() = onFinished()

    override fun onFabClicked() = navigateToDetail(postId)

    class Factory(
        private val repository: PostRepository,
    ) : Detail2Component.Factory {

        override fun invoke(
            componentContext: ComponentContext,
            postId: String,
            navigateToDetail: (String) -> Unit,
            onFinished: () -> Unit,
        ): Detail2Component = DefaultDetail2Component(
            componentContext = componentContext,
            postId = postId,
            navigateToDetail = navigateToDetail,
            onFinished = onFinished,
            repository = repository,
        )
    }
}
