import org.kodein.di.DI
import presentation.root.rootFeatureModule

val kodeinDI = DI {
    importOnce(rootFeatureModule)
}