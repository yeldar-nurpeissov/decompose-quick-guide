package presentation.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import presentation.create.CreateComponent
import presentation.detail.Detail2Component
import presentation.detail.DetailComponent
import presentation.list.ListComponent

class DefaultRootComponent(
    componentContext: ComponentContext,
    private val listComponentFactory: ListComponent.Factory,
    private val detailComponentFactory: DetailComponent.Factory,
    private val detail2ComponentFactory: Detail2Component.Factory,
    private val createComponentFactory: CreateComponent.Factory,
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
                postClicked = { postId -> nav.pushNew(Config.Detail(postId)) },
                createNewPostClicked = { nav.pushNew(Config.Create) }
            )
        )

        is Config.Detail -> RootComponent.Child.Detail(
            detailComponentFactory(
                componentContext = componentContext,
                postId = config.postId,
                onFinished = { nav.pop() },
                navigateToDetail2 = {
                    nav.pushNew(Config.Detail2(it))
                }
            )
        )
        is Config.Detail2 -> RootComponent.Child.Detail2(
            detail2ComponentFactory(
                componentContext = componentContext,
                postId = config.postId,
                navigateToDetail = {
                    nav.pushNew(Config.Detail(it))
                },
                onFinished = { nav.pop() },
            )
        )

        Config.Create -> RootComponent.Child.Create(
            createComponentFactory(
                componentContext = componentContext,
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
        @Serializable
        data class Detail2(val postId: String) : Config

        @Serializable
        data object Create : Config
    }

    class Factory(
        private val listComponentFactory: ListComponent.Factory,
        private val detailComponentFactory: DetailComponent.Factory,
        private val detail2ComponentFactory: Detail2Component.Factory,
        private val createComponentFactory: CreateComponent.Factory,
    ) : RootComponent.Factory {
        override fun invoke(componentContext: ComponentContext): RootComponent {
            return DefaultRootComponent(
                componentContext = componentContext,
                listComponentFactory = listComponentFactory,
                detailComponentFactory = detailComponentFactory,
                detail2ComponentFactory = detail2ComponentFactory,
                createComponentFactory = createComponentFactory,
            )
        }
    }
}