package presentation.create

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import data.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.withContext
import presentation.create.model.CreateIntent
import presentation.create.model.CreateLabel
import presentation.create.model.CreateState
import util.ViewModel

class CreateViewModel(
    private val postRepository: PostRepository,
) : ViewModel<CreateState, CreateIntent, CreateLabel>() {

    @Composable
    override fun state(intents: SharedFlow<CreateIntent>): CreateState {
        return createState(intents)
    }

    @Composable
    private fun createState(intents: SharedFlow<CreateIntent>): CreateState {
        val title = remember { mutableStateOf("") }
        val description = remember { mutableStateOf("") }
        val author = remember { mutableStateOf("") }
        val loading = remember { mutableStateOf(false) }
        val canSave = remember {
            derivedStateOf {
                title.value.isNotBlank() && description.value.isNotBlank() && author.value.isNotBlank()
            }
        }

        LaunchedEffect(Unit) {
            intents.collect { intent ->
                when (intent) {
                    is CreateIntent.ChangeAuthor -> author.value = intent.value
                    is CreateIntent.ChangeDescription -> description.value = intent.value
                    is CreateIntent.ChangeTitle -> title.value = intent.value
                    CreateIntent.Save -> try {
                        loading.value = true
                        withContext(Dispatchers.Default) {
                            postRepository.createPost(title.value, description.value, author.value)
                        }
                        labels.trySend(CreateLabel.PostCreated)
                    } catch (e: Throwable) {
                        // handle error
                        // labels.trySend(Label.ShowAlertDialog)
                    } finally {
                        loading.value = false
                    }
                }
            }
        }

        return CreateState(
            title = title.value,
            description = description.value,
            author = author.value,
            loading = loading.value,
            canSave = canSave.value,
        )
    }

    class Factory(
        private val repository: PostRepository,
    ) {
        operator fun invoke() = CreateViewModel(repository)
    }
}