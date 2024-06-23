package data.di

import data.repository.DefaultPostRepository
import data.repository.PostRepository
import org.kodein.di.DI
import org.kodein.di.bindSingleton

val postRepositoryModule = DI.Module("postRepositoryModule") {
    bindSingleton<PostRepository> { DefaultPostRepository() }
}