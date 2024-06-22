package presentation.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import presentation.detail.DetailComponent
import presentation.list.ListComponent

class DefaultRootComponent(
    componentContext: ComponentContext,
    private val listComponentFactory: ListComponent.Factory,
    private val detailComponentFactory: DetailComponent.Factory,
) : RootComponent, ComponentContext by componentContext {

    private val nav = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = nav,
        serializer = Config.serializer(),
        initialConfiguration = Config.List,
        handleBackButton = true,
        childFactory = ::child,
    )

    private fun child(
        config: Config,
        componentContext: ComponentContext
    ): RootComponent.Child = when (config) {
        Config.List -> RootComponent.Child.List(
            listComponentFactory(
                componentContext = componentContext,
                postClicked = { postId -> nav.pushNew(Config.Detail(postId)) }
            )
        )

        is Config.Detail -> RootComponent.Child.Detail(
            detailComponentFactory(
                componentContext = componentContext,
                postId = config.postId,
                onFinished = { nav.pop() },
            )
        )
    }


    @Serializable
    private sealed interface Config {
        @Serializable
        data object List : Config

        @Serializable
        data class Detail(val postId: String) : Config
    }

    class Factory(
        private val listComponentFactory: ListComponent.Factory,
        private val detailComponentFactory: DetailComponent.Factory,
    ) : RootComponent.Factory {
        override fun invoke(componentContext: ComponentContext): RootComponent {
            return DefaultRootComponent(
                listComponentFactory = listComponentFactory,
                detailComponentFactory = detailComponentFactory,
                componentContext = componentContext,
            )
        }
    }
}