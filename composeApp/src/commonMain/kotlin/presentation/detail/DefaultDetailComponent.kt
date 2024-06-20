package presentation.detail

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import model.Post

class DefaultDetailComponent(
    componentContext: ComponentContext,
    post: Post,
    private val onFinished: () -> Unit,
) : DetailComponent, ComponentContext by componentContext {

    override val model: Value<Post> = MutableValue(post)

    override fun onBackPressed() = onFinished()
}
