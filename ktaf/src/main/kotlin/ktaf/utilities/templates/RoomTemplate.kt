package ktaf.utilities.templates

import ktaf.assets.characters.PlayableCharacter
import ktaf.assets.locations.Room
import kotlin.reflect.KClass

/**
 * Provides a template for producing [Room] objects.
 */
public open class RoomTemplate<TDerived : RoomTemplate<TDerived>> {
    protected open fun onCreate(): Room {
        throw NotImplementedError()
    }

    protected open fun onCreate(pC: PlayableCharacter): Room {
        return onCreate()
    }

    protected companion object {
        protected inline fun <reified T : RoomTemplate<T>> create(): Room {
            val clazz: KClass<T> = T::class
            return clazz.java.getDeclaredConstructor().newInstance().onCreate()
        }

        protected inline fun <reified T : RoomTemplate<T>> create(pC: PlayableCharacter): Room {
            val clazz: KClass<T> = T::class
            return clazz.java.getDeclaredConstructor().newInstance().onCreate(pC)
        }
    }
}
