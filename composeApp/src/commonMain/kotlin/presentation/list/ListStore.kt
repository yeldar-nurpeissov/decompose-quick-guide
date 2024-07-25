package presentation.list

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import data.model.Post
import data.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import presentation.list.ListStore.State

internal interface ListStore : Store<Nothing, State, Any> {
    data class State(
        val items: List<Post> = emptyList()
    )
}

internal class ListStoreFactory(
    private val storeFactory: StoreFactory,
    private val postRepository: PostRepository,
) {

    fun create(): ListStore =
        object : ListStore, Store<Nothing, State, Any> by storeFactory.create(
            name = "ListStore",
            initialState = State(),
            bootstrapper = BootstrapperImpl(postRepository),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
        data class NewItemsReceived(val items: List<Post>) : Action
    }

    private sealed interface Msg {
        data class UpdateItems(val items: List<Post>) : Msg
    }

    private class BootstrapperImpl(
        private val repository: PostRepository,
    ) : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                repository.getAllPosts()
                    .flowOn(Dispatchers.Default)
                    .collect { items ->
                        dispatch(Action.NewItemsReceived(items))
                    }
            }
        }
    }

    private class ExecutorImpl : CoroutineExecutor<Nothing, Action, State, Msg, Any>() {
        override fun executeAction(action: Action) {
            when (action) {
                is Action.NewItemsReceived -> {
                    dispatch(Msg.UpdateItems(action.items))
                }
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(message: Msg): State =
            when (message) {
                is Msg.UpdateItems -> copy(items = message.items)
            }
    }
}
