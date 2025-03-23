package presentation.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import data.model.Post
import data.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.flowOn
import presentation.list.model.ListState
import util.ViewModel

class ListViewModel(
    private val repository: PostRepository,
) : ViewModel<ListState, Nothing, Nothing>() {

    @Composable
    override fun state(intents: SharedFlow<Nothing>): ListState {
        val posts = remember { mutableStateListOf<Post>() }

        LaunchedEffect(Unit) {
            repository
                .getAllPosts()
                .flowOn(Dispatchers.Default)
                .collect {
                    posts.clear()
                    posts.addAll(it)
                }
        }

        return ListState(posts.toList())
    }

    class Factory(
        private val repository: PostRepository,
    ) {
        operator fun invoke() = ListViewModel(repository)
    }
}