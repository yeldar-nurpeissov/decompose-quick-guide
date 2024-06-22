package presentation.detail

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import data.model.Post

interface DetailComponent {
    val model: Value<Post>

    fun onBackPressed()

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            postId: String,
            onFinished: () -> Unit,
        ): DetailComponent
    }
}