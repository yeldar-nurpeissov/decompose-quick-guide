package presentation.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import data.model.Post
import data.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import presentation.detail.model.DetailState
import util.ViewModel

class DetailViewModel(
    private val postId: String,
    private val postRepository: PostRepository,
) : ViewModel<DetailState, Nothing, Nothing>() {

    @Composable
    override fun state(intents: SharedFlow<Nothing>): DetailState {
        return createState()
    }

    @Composable
    private fun createState(): DetailState {
        var post by remember { mutableStateOf<Post?>(null) }
        var error by remember { mutableStateOf<String?>(null) }

        LaunchedEffect(postId) {
            postRepository.getPost(postId)
                .flowOn(Dispatchers.Default)
                .catch { error = it.message ?: "Failed to load the post" }
                .collect { post = it }
        }

        return when {
            post != null -> DetailState.Success(post!!)
            !error.isNullOrBlank() -> DetailState.Error(error.orEmpty())
            else -> DetailState.Loading
        }
    }

    class Factory(
        private val repository: PostRepository,
    ) {
        operator fun invoke(postId: String) = DetailViewModel(postId, repository)
    }
}