package presentation.root

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import presentation.detail.detailFeatureModule
import presentation.list.listFeatureModule

val rootFeatureModule = DI.Module("rootFeatureModule") {
    importAll(detailFeatureModule, listFeatureModule)

    bindSingleton<RootComponent.Factory> {
        DefaultRootComponent.Factory(
            detailComponentFactory = instance(),
            listComponentFactory = instance(),
        )
    }
}