package ktaf.utilities.templates

import ktaf.assets.characters.PlayableCharacter
import ktaf.assets.locations.Region
import kotlin.reflect.KClass

/**
 * Provides a template for producing [Region] objects.
 */
public open class RegionTemplate<TDerived : RegionTemplate<TDerived>> {
    protected open fun onCreate(): Region {
        throw NotImplementedError()
    }

    protected open fun onCreate(pC: PlayableCharacter): Region {
        return onCreate()
    }

    protected companion object {
        protected inline fun <reified T : RegionTemplate<T>> create(): Region {
            val clazz: KClass<T> = T::class
            return clazz.java.getDeclaredConstructor().newInstance().onCreate()
        }

        protected inline fun <reified T : RegionTemplate<T>> create(pC: PlayableCharacter): Region {
            val clazz: KClass<T> = T::class
            return clazz.java.getDeclaredConstructor().newInstance().onCreate(pC)
        }
    }
}
