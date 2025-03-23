package presentation.create.model

data class CreateState(
    val title: String = "",
    val description: String = "",
    val author: String = "",
    val canSave: Boolean = false,
    val loading: Boolean = false,
)