package presentation.create

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import kotlinx.coroutines.launch
import util.asValue

internal class DefaultCreateComponent(
    componentContext: ComponentContext,
    private val createStoreFactory: CreateStoreFactory,
    private val onFinished: () -> Unit,
) : CreateComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { createStoreFactory.create() }

    init {
        coroutineScope().launch {
            store.labels.collect { label ->
                when (label) {
                    CreateStore.Label.PostCreated -> onFinished()
                }
            }
        }
    }

    override val model: Value<CreateComponent.Model> = store.asValue().map {
        CreateComponent.Model(
            title = it.title,
            description = it.description,
            author = it.author,
            canSave = it.canSave,
            loading = it.loading,
        )
    }

    override fun onBackPressed() = onFinished()

    override fun onTitleChanged(value: String) {
        store.accept(CreateStore.Intent.ChangeTitle(value))
    }

    override fun onDescriptionChanged(value: String) {
        store.accept(CreateStore.Intent.ChangeDescription(value))
    }

    override fun onAuthorChanged(value: String) {
        store.accept(CreateStore.Intent.ChangeAuthor(value))
    }

    override fun onSaveClicked() {
        store.accept(CreateStore.Intent.Save)
    }

    class Factory(
        private val createStoreFactory: CreateStoreFactory,
    ) : CreateComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
            onFinished: () -> Unit,
        ): CreateComponent = DefaultCreateComponent(
            componentContext = componentContext,
            createStoreFactory = createStoreFactory,
            onFinished = onFinished,
        )
    }
}
