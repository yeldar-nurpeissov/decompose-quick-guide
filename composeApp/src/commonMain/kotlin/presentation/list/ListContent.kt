package presentation.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState

@Composable
fun ListContent(
    component: ListComponent,
    modifier: Modifier = Modifier,
) {
    val state by component.model.subscribeAsState()

    Scaffold(
        modifier = modifier,
        topBar = { TopAppBar(title = { Text("List") }) }
    ) { paddingValues ->
        LazyColumn(
            state = rememberLazyListState(),
            modifier = Modifier.padding(paddingValues)
        ) {
            items(state) { post ->
                Row(
                    modifier = Modifier
                    .fillMaxWidth()
                    .clickable { component.onPostClicked(post) }
                    .padding(16.dp)
                ) {
                    Text(post.title)
                }
            }
        }
    }
}