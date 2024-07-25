import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.timetravel.store.TimeTravelStoreFactory
import data.repository.DefaultPostRepository
import data.repository.PostRepository
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import presentation.create.CreateComponent
import presentation.create.DefaultCreateComponent
import presentation.detail.DefaultDetailComponent
import presentation.detail.DetailComponent
import presentation.detail.DetailStoreFactory
import presentation.list.DefaultListComponent
import presentation.list.ListComponent
import presentation.list.ListStoreFactory
import presentation.root.DefaultRootComponent
import presentation.root.RootComponent

val kodeinDI = DI {
    // repository
    bindSingleton<PostRepository> { DefaultPostRepository() }

    // store
    bindSingleton<StoreFactory> {
        LoggingStoreFactory(TimeTravelStoreFactory())
    }

    // detail
    bindSingleton<DetailComponent.Factory> {
        DefaultDetailComponent.Factory(
            detailStoreFactory = instance(),
        )
    }
    bindSingleton {
        DetailStoreFactory(
            storeFactory = instance(),
            postRepository = instance(),
        )
    }

    // list
    bindSingleton<ListComponent.Factory> {
        DefaultListComponent.Factory(
            listStoreFactory = instance(),
        )
    }
    bindSingleton {
        ListStoreFactory(
            storeFactory = instance(),
            postRepository = instance(),
        )
    }

    // create
    bindSingleton<CreateComponent.Factory> {
        DefaultCreateComponent.Factory()
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