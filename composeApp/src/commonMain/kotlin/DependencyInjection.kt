import data.repository.DefaultPostRepository
import data.repository.PostRepository
import presentation.detail.DefaultDetailComponent
import presentation.detail.DetailComponent
import presentation.list.DefaultListComponent
import presentation.list.ListComponent
import presentation.root.DefaultRootComponent
import presentation.root.RootComponent

object DependencyInjection {
    val repository: PostRepository = DefaultPostRepository()

    val detailComponentFactory: DetailComponent.Factory = DefaultDetailComponent.Factory(
        repository = repository
    )
    val listComponentFactory: ListComponent.Factory = DefaultListComponent.Factory(
        repository = repository,
    )

    val rootComponentFactory: RootComponent.Factory = DefaultRootComponent.Factory(
        detailComponentFactory = detailComponentFactory,
        listComponentFactory = listComponentFactory,
    )
}