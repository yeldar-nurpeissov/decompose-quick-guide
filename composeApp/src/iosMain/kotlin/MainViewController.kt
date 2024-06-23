import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.ApplicationLifecycle
import presentation.root.RootComponent

fun MainViewController() = ComposeUIViewController {
    val rootComponent = remember {
        val rootComponentFactory: RootComponent.Factory by koin.inject()
        rootComponentFactory(DefaultComponentContext(ApplicationLifecycle()))
    }
    App(rootComponent = rootComponent)
}