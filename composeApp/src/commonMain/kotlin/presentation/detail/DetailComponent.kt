package presentation.detail

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.StateFlow
import presentation.detail.model.DetailState

interface DetailComponent {
    val state: StateFlow<DetailState>

    fun onBackPressed()

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            postId: String,
            onFinished: () -> Unit,
        ): DetailComponent
    }
}