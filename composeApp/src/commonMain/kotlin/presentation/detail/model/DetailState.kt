package presentation.detail.model

import data.model.Post

sealed interface DetailState {
    data object Loading : DetailState
    data class Success(val post: Post) : DetailState
    data class Error(val message: String) : DetailState
}