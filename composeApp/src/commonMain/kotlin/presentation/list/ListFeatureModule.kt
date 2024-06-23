package presentation.list

import data.di.postRepositoryModule
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val listFeatureModule = DI.Module("listFeatureModule") {
    importOnce(postRepositoryModule)

    bindSingleton<ListComponent.Factory> {
        DefaultListComponent.Factory(
            repository = instance(),
        )
    }
}