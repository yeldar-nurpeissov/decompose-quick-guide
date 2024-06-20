package presentation.detail

import com.arkivanov.decompose.value.Value
import model.Post

interface DetailComponent {
    val model: Value<Post>

    fun onBackPressed()
}