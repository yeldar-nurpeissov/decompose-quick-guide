import data.repository.DefaultPostRepository
import data.repository.PostRepository
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import presentation.detail.DefaultDetailComponent
import presentation.detail.DetailComponent
import presentation.list.DefaultListComponent
import presentation.list.ListComponent
import presentation.root.DefaultRootComponent
import presentation.root.RootComponent

val koin by lazy { initKoin().koin }

fun initKoin(appDeclaration: KoinAppDeclaration? = null) = startKoin {
    appDeclaration?.invoke(this)
    modules(appModule)
}

val appModule = module {
    single<PostRepository> { DefaultPostRepository() }

    single<DetailComponent.Factory> {
        DefaultDetailComponent.Factory(
            repository = get(),
        )
    }

    single<ListComponent.Factory> {
        DefaultListComponent.Factory(
            repository = get(),
        )
    }

    single<RootComponent.Factory> {
        DefaultRootComponent.Factory(
            detailComponentFactory = get(),
            listComponentFactory = get(),
        )
    }
}