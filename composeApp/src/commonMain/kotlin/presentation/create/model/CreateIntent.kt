package presentation.create.model

sealed interface CreateIntent {
    data class ChangeTitle(val value: String) : CreateIntent
    data class ChangeDescription(val value: String) : CreateIntent
    data class ChangeAuthor(val value: String) : CreateIntent
    data object Save : CreateIntent
}