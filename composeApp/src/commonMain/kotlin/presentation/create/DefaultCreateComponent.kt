package presentation.create

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.decompose.value.update
import kotlinx.serialization.Serializable

class DefaultCreateComponent(
    componentContext: ComponentContext,
    private val onFinished: () -> Unit,
) : CreateComponent, ComponentContext by componentContext {

    private val state = MutableValue(State())

    override val model: Value<CreateComponent.Model> = state.map {
        CreateComponent.Model(
            title = it.title,
            description = it.description,
            author = it.author,
        )
    }

    override fun onBackPressed() = onFinished()

    override fun onNameChanged(value: String) {
        state.update { it.copy(title = value) }
    }

    override fun onDescriptionChanged(value: String) {
        state.update { it.copy(description = value) }
    }

    override fun onAuthorChanged(value: String) {
        state.update { it.copy(author = value) }
    }

    class Factory : CreateComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
            onFinished: () -> Unit,
        ): CreateComponent = DefaultCreateComponent(
            componentContext = componentContext,
            onFinished = onFinished,
        )
    }
}

@Serializable
private data class State(
    val title: String = "",
    val description: String = "",
    val author: String = "",
)