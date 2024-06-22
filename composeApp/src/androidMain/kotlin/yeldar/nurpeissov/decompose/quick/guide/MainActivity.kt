package yeldar.nurpeissov.decompose.quick.guide

import App
import DependencyInjection
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.defaultComponentContext
import presentation.root.RootComponent

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val rootComponentFactory: RootComponent.Factory = DependencyInjection.rootComponentFactory

        // Always create the root component outside Compose on the main thread
        val rootComponent = rootComponentFactory(defaultComponentContext())

        setContent {
            App(rootComponent = rootComponent)
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
//    App()
}