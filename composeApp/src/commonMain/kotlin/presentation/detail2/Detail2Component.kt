package presentation.detail

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import data.model.Post

interface Detail2Component {
    val model: Value<Post>

    fun onBackPressed()
    fun onFabClicked()

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            postId: String,
            navigateToDetail: (String) -> Unit,
            onFinished: () -> Unit,
        ): Detail2Component
    }
}