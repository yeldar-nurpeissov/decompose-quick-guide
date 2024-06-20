import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import presentation.root.DefaultRootComponent

fun MainViewController() = ComposeUIViewController {
    val root = remember {
        DefaultRootComponent(DefaultComponentContext(LifecycleRegistry()))
    }
    App(rootComponent = root)
}