package presentation.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import model.Post

@Composable
fun DetailContent(
    component: DetailComponent,
    modifier: Modifier = Modifier,
) {
    val state by component.model.subscribeAsState()

    DetailContent(
        modifier = modifier,
        onBackPressed = component::onBackPressed,
        post = state,
    )
}

@Composable
fun DetailContent(
    modifier: Modifier,
    onBackPressed: () -> Unit,
    post: Post
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Detail") },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(Icons.Outlined.ArrowBackIosNew, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(post.title)
            Text(post.description)
            Text(post.author)
        }
    }
}