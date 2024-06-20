package presentation.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState

@Composable
fun DetailContent(
    component: DetailComponent,
    modifier: Modifier = Modifier,
) {
    val state by component.model.subscribeAsState()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Detail") },
                navigationIcon = {
                    IconButton(onClick = component::onBackPressed) {
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
            Text(state.title)
            Text(state.description)
            Text(state.author)
        }
    }
}