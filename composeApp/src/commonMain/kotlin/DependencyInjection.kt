import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import presentation.root.rootFeatureModule

val koin by lazy { initKoin().koin }

fun initKoin(appDeclaration: KoinAppDeclaration? = null) = startKoin {
    appDeclaration?.invoke(this)
    modules(appModules())
}

fun appModules() = listOf(rootFeatureModule)