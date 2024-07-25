package presentation.detail

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import data.model.Post

interface DetailComponent {
    val model: Value<Model>

    sealed interface Model {
        data object Loading : Model
        data class Error(val errorText: String) : Model
        data class Success(val post: Post) : Model
    }

    fun onBackPressed()

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            postId: String,
            onFinished: () -> Unit,
        ): DetailComponent
    }
}