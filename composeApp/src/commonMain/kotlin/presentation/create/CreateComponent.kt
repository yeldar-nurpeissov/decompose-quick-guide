package presentation.create

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.StateFlow
import presentation.create.model.CreateState

interface CreateComponent {
    val state: StateFlow<CreateState>

    fun onBackPressed()

    fun onTitleChanged(value: String)
    fun onDescriptionChanged(value: String)
    fun onAuthorChanged(value: String)

    fun onSaveClicked()

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            onFinished: () -> Unit,
        ): CreateComponent
    }
}