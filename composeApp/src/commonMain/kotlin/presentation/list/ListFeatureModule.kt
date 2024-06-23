package presentation.list

import data.di.postRepositoryModule
import org.koin.dsl.module

val listFeatureModule = module {
    includes(postRepositoryModule)

    single<ListComponent.Factory> {
        DefaultListComponent.Factory(
            repository = get(),
        )
    }
}