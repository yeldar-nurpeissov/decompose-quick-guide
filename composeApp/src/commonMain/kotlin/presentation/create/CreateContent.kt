package presentation.create

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CreateContent(
    component: CreateComponent,
    modifier: Modifier = Modifier,
) {
    val state by component.state.collectAsState()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Create") },
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
                .fillMaxSize()
                .padding(paddingValues)
                .padding(32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OutlinedTextField(
                value = state.title,
                onValueChange = component::onTitleChanged,
                label = { Text("Title") },
            )
            OutlinedTextField(
                value = state.description,
                onValueChange = component::onDescriptionChanged,
                label = { Text("Description") },
            )
            OutlinedTextField(
                value = state.author,
                onValueChange = component::onAuthorChanged,
                label = { Text("Author") },
            )

            AnimatedContent(state.loading) { isLoading ->
                if (isLoading) CircularProgressIndicator()
                else {
                    Button(
                        onClick = component::onSaveClicked,
                        enabled = state.canSave,
                    ) {
                        Text("Save")
                    }
                }
            }
        }
    }
}