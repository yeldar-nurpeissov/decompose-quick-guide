package presentation.detail

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlinx.coroutines.flow.StateFlow
import presentation.detail.model.DetailState

internal class DefaultDetailComponent(
    componentContext: ComponentContext,
    postId: String,
    detailViewModelFactory: DetailViewModel.Factory,
    private val onFinished: () -> Unit,
) : DetailComponent, ComponentContext by componentContext {

    private val viewModel = instanceKeeper.getOrCreate { detailViewModelFactory(postId) }

    override val state: StateFlow<DetailState> = viewModel.state

    override fun onBackPressed() = onFinished()

    class Factory(
        private val detailViewModelFactory: DetailViewModel.Factory,
    ) : DetailComponent.Factory {

        override fun invoke(
            componentContext: ComponentContext,
            postId: String,
            onFinished: () -> Unit,
        ): DetailComponent = DefaultDetailComponent(
            componentContext = componentContext,
            postId = postId,
            onFinished = onFinished,
            detailViewModelFactory = detailViewModelFactory,
        )
    }
}
