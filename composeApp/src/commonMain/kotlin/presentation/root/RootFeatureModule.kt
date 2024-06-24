package presentation.root

import org.koin.dsl.module
import presentation.detail.detailFeatureModule
import presentation.list.listFeatureModule

val rootFeatureModule = module {
    includes(detailFeatureModule, listFeatureModule)

    single<RootComponent.Factory> {
        DefaultRootComponent.Factory(
            detailComponentFactory = get(),
            listComponentFactory = get(),
        )
    }
}