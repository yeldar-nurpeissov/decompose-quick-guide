package presentation.detail

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import util.asValue

internal class DefaultDetailComponent(
    componentContext: ComponentContext,
    postId: String,
    detailStoreFactory: DetailStoreFactory,
    private val onFinished: () -> Unit,
) : DetailComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { detailStoreFactory.create(postId) }

    override val model: Value<DetailComponent.Model> = store.asValue().map {
        when (it) {
            DetailStore.State.Loading -> DetailComponent.Model.Loading
            is DetailStore.State.Error -> DetailComponent.Model.Error(it.message)
            is DetailStore.State.Success -> DetailComponent.Model.Success(it.post)
        }
    }

    override fun onBackPressed() = onFinished()

    class Factory(
        private val detailStoreFactory: DetailStoreFactory,
    ) : DetailComponent.Factory {

        override fun invoke(
            componentContext: ComponentContext,
            postId: String,
            onFinished: () -> Unit,
        ): DetailComponent = DefaultDetailComponent(
            componentContext = componentContext,
            postId = postId,
            onFinished = onFinished,
            detailStoreFactory = detailStoreFactory,
        )
    }
}
