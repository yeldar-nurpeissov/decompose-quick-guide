import data.repository.DefaultPostRepository
import data.repository.PostRepository
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import presentation.detail.DefaultDetailComponent
import presentation.detail.DetailComponent
import presentation.list.DefaultListComponent
import presentation.list.ListComponent
import presentation.root.DefaultRootComponent
import presentation.root.RootComponent

val kodeinDI = DI {
    bindSingleton<PostRepository> { DefaultPostRepository() }

    bindSingleton<DetailComponent.Factory> {
        DefaultDetailComponent.Factory(
            repository = instance(),
        )
    }
    bindSingleton<ListComponent.Factory> {
        DefaultListComponent.Factory(
            repository = instance(),
        )
    }
    bindSingleton<RootComponent.Factory> {
        DefaultRootComponent.Factory(
            detailComponentFactory = instance(),
            listComponentFactory = instance(),
        )
    }
}