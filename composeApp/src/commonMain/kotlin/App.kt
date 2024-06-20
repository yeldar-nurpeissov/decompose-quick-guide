import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.root.RootComponent
import presentation.root.RootContent

@Composable
@Preview
fun App(rootComponent: RootComponent) {
    MaterialTheme {
        RootContent(
            component = rootComponent,
            modifier = Modifier.fillMaxSize(),
        )
    }
}