import data.repository.DefaultPostRepository
import data.repository.PostRepository
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import presentation.create.CreateComponent
import presentation.create.CreateViewModel
import presentation.create.DefaultCreateComponent
import presentation.detail.DefaultDetailComponent
import presentation.detail.DetailComponent
import presentation.detail.DetailViewModel
import presentation.list.DefaultListComponent
import presentation.list.ListComponent
import presentation.list.ListViewModel
import presentation.root.DefaultRootComponent
import presentation.root.RootComponent

val kodeinDI = DI {
    // repository
    bindSingleton<PostRepository> { DefaultPostRepository() }

    // detail
    bindSingleton<DetailComponent.Factory> {
        DefaultDetailComponent.Factory(instance())
    }
    bindSingleton {
        DetailViewModel.Factory(instance())
    }

    // list
    bindSingleton<ListComponent.Factory> {
        DefaultListComponent.Factory(instance())
    }
    bindSingleton {
        ListViewModel.Factory(instance())
    }

    // create
    bindSingleton<CreateComponent.Factory> {
        DefaultCreateComponent.Factory(instance())
    }
    bindSingleton {
        CreateViewModel.Factory(instance())
    }

    // root
    bindSingleton<RootComponent.Factory> {
        DefaultRootComponent.Factory(
            detailComponentFactory = instance(),
            listComponentFactory = instance(),
            createComponentFactory = instance(),
        )
    }
}