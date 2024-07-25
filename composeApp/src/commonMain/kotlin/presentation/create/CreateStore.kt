package presentation.create

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import data.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import presentation.create.CreateStore.Intent
import presentation.create.CreateStore.Label
import presentation.create.CreateStore.State

internal interface CreateStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data class ChangeTitle(val value: String) : Intent
        data class ChangeDescription(val value: String) : Intent
        data class ChangeAuthor(val value: String) : Intent
        data object Save : Intent
    }

    data class State(
        val title: String = "",
        val description: String = "",
        val author: String = "",
        val canSave: Boolean = false,
        val loading: Boolean = false,
    )

    sealed interface Label {
        data object PostCreated : Label
    }
}

internal class CreateStoreFactory(
    private val storeFactory: StoreFactory,
    private val postRepository: PostRepository,
) {

    fun create(): CreateStore =
        object : CreateStore, Store<Intent, State, Label> by storeFactory.create(
            name = "CreateStore",
            initialState = State(),
            executorFactory = { ExecutorImpl(postRepository) },
            reducer = ReducerImpl
        ) {}

    private sealed interface Msg {
        data class TitleChanged(val title: String) : Msg
        data class DescriptionChanged(val description: String) : Msg
        data class AuthorChanged(val author: String) : Msg
        data class EnableSaveButton(val enabled: Boolean) : Msg
        data class Loading(val loading: Boolean) : Msg
    }

    private class ExecutorImpl(
        private val postRepository: PostRepository,
    ) : CoroutineExecutor<Intent, Nothing, State, Msg, Label>() {
        override fun executeIntent(intent: Intent) {
            when (intent) {
                is Intent.ChangeTitle -> {
                    dispatch(Msg.TitleChanged(intent.value))
                    dispatch(Msg.EnableSaveButton(canSave()))
                }

                is Intent.ChangeDescription -> {
                    dispatch(Msg.DescriptionChanged(intent.value))
                    dispatch(Msg.EnableSaveButton(canSave()))
                }

                is Intent.ChangeAuthor -> {
                    dispatch(Msg.AuthorChanged(intent.value))
                    dispatch(Msg.EnableSaveButton(canSave()))
                }

                Intent.Save -> scope.launch {
                    val state = state()
                    try {
                        dispatch(Msg.Loading(true))
                        withContext(Dispatchers.Default) {
                            postRepository.createPost(
                                title = state.title,
                                description = state.description,
                                author = state.author
                            )
                        }
                        publish(Label.PostCreated)
                    } catch (e: Throwable) {
                        // handle error
                        // publish(Label.ShowAlertDialog)
                    } finally {
                        dispatch(Msg.Loading(false))
                    }
                }
            }
        }

        private fun canSave(): Boolean = with(state()) {
            title.isNotBlank() && description.isNotBlank() && author.isNotBlank()
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(message: Msg): State =
            when (message) {
                is Msg.TitleChanged -> copy(title = message.title)
                is Msg.DescriptionChanged -> copy(description = message.description)
                is Msg.AuthorChanged -> copy(author = message.author)
                is Msg.EnableSaveButton -> copy(canSave = message.enabled)
                is Msg.Loading -> copy(loading = message.loading)
            }
    }
}
