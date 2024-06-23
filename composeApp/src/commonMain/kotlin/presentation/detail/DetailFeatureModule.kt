package presentation.detail

import data.di.postRepositoryModule
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val detailFeatureModule = DI.Module("detailFeatureModule") {
    importOnce(postRepositoryModule)

    bindSingleton<DetailComponent.Factory> {
        DefaultDetailComponent.Factory(
            repository = instance(),
        )
    }
}