package yeldar.nurpeissov.decompose.quick.guide

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.defaultComponentContext
import model.Post
import presentation.detail.DetailContent
import presentation.root.DefaultRootComponent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Always create the root component outside Compose on the main thread
        val rootComponent = DefaultRootComponent(defaultComponentContext())

        setContent {
            App(rootComponent = rootComponent)
        }
    }
}

@Preview
@Composable
fun DetailContentPreview() {
    DetailContent(
        modifier = Modifier.fillMaxSize(),
        onBackPressed = {},
        post = Post(
            id = "1",
            title = "Title",
            description = "Description",
            author = "Author"
        )
    )
}