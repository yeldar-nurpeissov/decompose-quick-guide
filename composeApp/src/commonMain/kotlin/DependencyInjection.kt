import data.repository.DefaultPostRepository
import data.repository.PostRepository
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import presentation.create.CreateComponent
import presentation.create.DefaultCreateComponent
import presentation.detail.DefaultDetail2Component
import presentation.detail.DefaultDetailComponent
import presentation.detail.Detail2Component
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
    bindSingleton<Detail2Component.Factory> {
        DefaultDetail2Component.Factory(
            repository = instance(),
        )
    }
    bindSingleton<ListComponent.Factory> {
        DefaultListComponent.Factory(
            repository = instance(),
        )
    }
    bindSingleton<CreateComponent.Factory> {
        DefaultCreateComponent.Factory()
    }
    bindSingleton<RootComponent.Factory> {
        DefaultRootComponent.Factory(
            detailComponentFactory = instance(),
            detail2ComponentFactory = instance(),
            listComponentFactory = instance(),
            createComponentFactory = instance(),
        )
    }
}