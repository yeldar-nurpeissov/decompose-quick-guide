package presentation.create

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value

interface CreateComponent {
    val model: Value<Model>

    fun onBackPressed()

    fun onTitleChanged(value: String)
    fun onDescriptionChanged(value: String)
    fun onAuthorChanged(value: String)

    fun onSaveClicked()

    data class Model(
        val title: String,
        val description: String,
        val author: String,
        val canSave: Boolean,
        val loading: Boolean,
    )

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            onFinished: () -> Unit,
        ): CreateComponent
    }
}