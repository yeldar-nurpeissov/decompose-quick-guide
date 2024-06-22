import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.ApplicationLifecycle

private val rootComponentFactory = DependencyInjection.rootComponentFactory

fun MainViewController() = ComposeUIViewController {
    val rootComponent = remember {
        rootComponentFactory(DefaultComponentContext(ApplicationLifecycle()))
    }
    App(rootComponent = rootComponent)
}