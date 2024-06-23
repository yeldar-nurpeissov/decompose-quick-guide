import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.extensions.compose.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import org.kodein.di.instance
import presentation.root.RootComponent

fun main() {
    val lifecycle = LifecycleRegistry()

    val rootComponentFactory: RootComponent.Factory by kodeinDI.instance()

    val rootComponent = runOnUiThread {
        rootComponentFactory(
            componentContext = DefaultComponentContext(lifecycle),
        )
    }
    application {
        val windowState = rememberWindowState()

        Window(
            onCloseRequest = ::exitApplication,
            state = windowState,
            title = "Decompose Quick Guide",
        ) {
            LifecycleController(
                lifecycleRegistry = lifecycle,
                windowState = windowState,
                windowInfo = LocalWindowInfo.current,
            )

            App(rootComponent = rootComponent)
        }
    }
}