package data.di

import data.repository.DefaultPostRepository
import data.repository.PostRepository
import org.koin.dsl.module

val postRepositoryModule = module {
    single<PostRepository> { DefaultPostRepository() }
}