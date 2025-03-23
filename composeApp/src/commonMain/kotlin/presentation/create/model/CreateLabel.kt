package presentation.create.model

sealed interface CreateLabel {
    data object PostCreated : CreateLabel
}