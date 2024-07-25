package presentation.detail

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import data.model.Post
import data.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import presentation.detail.DetailStore.State

internal interface DetailStore : Store<Nothing, State, Any> {
    sealed interface State {
        data object Loading : State
        data class Success(val post: Post) : State
        data class Error(val message: String) : State
    }
}

internal class DetailStoreFactory(
    private val storeFactory: StoreFactory,
    private val postRepository: PostRepository,
) {

    fun create(postId: String): DetailStore =
        object : DetailStore, Store<Nothing, State, Any> by storeFactory.create(
            name = "DetailStore",
            initialState = State.Loading,
            bootstrapper = BootstrapperImpl(
                postId = postId,
                postRepository = postRepository,
            ),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
        data class NewPostLoaded(val post: Post) : Action
        data class PostLoadFailed(val throwable: Throwable) : Action
    }

    private sealed interface Msg {
        data class UpdatePost(val post: Post) : Msg
        data class ShowError(val errorText: String) : Msg
    }

    private class BootstrapperImpl(
        private val postId: String,
        private val postRepository: PostRepository,
    ) : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                postRepository.getPost(postId)
                    .flowOn(Dispatchers.Default)
                    .catch { dispatch(Action.PostLoadFailed(it)) }
                    .collect { dispatch(Action.NewPostLoaded(it)) }
            }
        }
    }

    private class ExecutorImpl : CoroutineExecutor<Nothing, Action, State, Msg, Any>() {
        override fun executeAction(action: Action) {
            when (action) {
                is Action.NewPostLoaded -> {
                    dispatch(Msg.UpdatePost(action.post))
                }

                is Action.PostLoadFailed -> {
                    val errorText = action.throwable.message ?: "Failed to load the post"
                    dispatch(Msg.ShowError(errorText))
                }
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(message: Msg): State =
            when (message) {
                is Msg.ShowError -> State.Error(message.errorText)
                is Msg.UpdatePost -> State.Success(message.post)
            }
    }
}
