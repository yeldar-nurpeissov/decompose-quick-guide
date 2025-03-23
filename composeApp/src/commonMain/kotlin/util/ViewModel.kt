package util

import androidx.compose.runtime.Composable
import app.cash.molecule.RecompositionMode
import app.cash.molecule.launchMolecule
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

abstract class ViewModel<State, Intent, Label> : InstanceKeeper.Instance {
    private val intents = MutableSharedFlow<Intent>(
        extraBufferCapacity = 20,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    val state: StateFlow<State> by lazy(LazyThreadSafetyMode.NONE) {
        scope.launchMolecule(mode = RecompositionMode.Immediate) {
            state(intents)
        }
    }
    val labels = Channel<Label>()

    fun accept(intent: Intent) {
        intents.tryEmit(intent)
    }

    open fun onCleared() {}

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
        onCleared()
    }

    @Composable
    protected abstract fun state(intents: SharedFlow<Intent>): State
}