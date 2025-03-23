package presentation.create

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import presentation.create.model.CreateIntent
import presentation.create.model.CreateLabel

internal class DefaultCreateComponent(
    componentContext: ComponentContext,
    private val createViewModelFactory: CreateViewModel.Factory,
    private val onFinished: () -> Unit,
) : CreateComponent, ComponentContext by componentContext {

    private val viewModel = instanceKeeper.getOrCreate { createViewModelFactory() }

    init {
        coroutineScope().launch {
            viewModel.labels.consumeEach { label ->
                when (label) {
                    CreateLabel.PostCreated -> onFinished()
                }
            }
        }
    }

    override val state = viewModel.state

    override fun onBackPressed() = onFinished()

    override fun onTitleChanged(value: String) {
        viewModel.accept(CreateIntent.ChangeTitle(value))
    }

    override fun onDescriptionChanged(value: String) {
        viewModel.accept(CreateIntent.ChangeDescription(value))
    }

    override fun onAuthorChanged(value: String) {
        viewModel.accept(CreateIntent.ChangeAuthor(value))
    }

    override fun onSaveClicked() {
        viewModel.accept(CreateIntent.Save)
    }

    class Factory(
        private val createViewModelFactory: CreateViewModel.Factory,
    ) : CreateComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
            onFinished: () -> Unit,
        ): CreateComponent = DefaultCreateComponent(
            componentContext = componentContext,
            createViewModelFactory = createViewModelFactory,
            onFinished = onFinished,
        )
    }
}
