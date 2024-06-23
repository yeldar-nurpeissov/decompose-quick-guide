package presentation.detail

import data.di.postRepositoryModule
import org.koin.dsl.module

val detailFeatureModule = module {
    includes(postRepositoryModule)

    single<DetailComponent.Factory> {
        DefaultDetailComponent.Factory(
            repository = get(),
        )
    }
}