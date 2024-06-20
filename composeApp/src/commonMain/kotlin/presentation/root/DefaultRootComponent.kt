package presentation.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import model.Post
import presentation.detail.DefaultDetailComponent
import presentation.list.DefaultListComponent

class DefaultRootComponent(
    componentContext: ComponentContext,
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
            DefaultListComponent(
                componentContext = componentContext,
                postClicked = { post -> nav.pushNew(Config.Detail(post)) }
            )
        )

        is Config.Detail -> RootComponent.Child.Detail(
            DefaultDetailComponent(
                componentContext = componentContext,
                post = config.post,
                onFinished = { nav.pop() },
            )
        )
    }


    @Serializable
    private sealed interface Config {
        @Serializable
        data object List : Config

        @Serializable
        data class Detail(val post: Post) : Config
    }
}