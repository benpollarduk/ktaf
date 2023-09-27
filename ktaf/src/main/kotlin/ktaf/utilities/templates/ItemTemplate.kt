package ktaf.utilities.templates

import ktaf.assets.Item
import ktaf.assets.characters.PlayableCharacter
import ktaf.assets.locations.Room
import kotlin.reflect.KClass

/**
 * Provides a template for producing [Item] objects.
 */
public open class ItemTemplate<TDerived : ItemTemplate<TDerived>> {
    protected open fun onCreate(): Item {
        throw NotImplementedError()
    }

    protected open fun onCreate(pC: PlayableCharacter, room: Room): Item {
        return onCreate()
    }

    protected companion object {
        protected inline fun <reified T : ItemTemplate<T>> create(): Item {
            val clazz: KClass<T> = T::class
            return clazz.java.getDeclaredConstructor().newInstance().onCreate()
        }

        protected inline fun <reified T : ItemTemplate<T>> create(pC: PlayableCharacter, room: Room): Item {
            val clazz: KClass<T> = T::class
            return clazz.java.getDeclaredConstructor().newInstance().onCreate(pC, room)
        }
    }
}
