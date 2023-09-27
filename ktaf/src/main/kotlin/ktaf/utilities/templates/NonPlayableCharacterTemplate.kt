package ktaf.utilities.templates

import ktaf.assets.characters.NonPlayableCharacter
import ktaf.assets.characters.PlayableCharacter
import ktaf.assets.locations.Room
import kotlin.reflect.KClass

/**
 * Provides a template for producing [NonPlayableCharacter] objects.
 */
public open class NonPlayableCharacterTemplate<TDerived : NonPlayableCharacterTemplate<TDerived>> {
    protected open fun onCreate(): NonPlayableCharacter {
        throw NotImplementedError()
    }

    protected open fun onCreate(pC: PlayableCharacter, room: Room): NonPlayableCharacter {
        return onCreate()
    }

    protected companion object {
        protected inline fun <reified T : NonPlayableCharacterTemplate<T>> create(): NonPlayableCharacter {
            val clazz: KClass<T> = T::class
            return clazz.java.getDeclaredConstructor().newInstance().onCreate()
        }

        protected inline fun <reified T : NonPlayableCharacterTemplate<T>> create(
            pC: PlayableCharacter,
            room: Room
        ): NonPlayableCharacter {
            val clazz: KClass<T> = T::class
            return clazz.java.getDeclaredConstructor().newInstance().onCreate(pC, room)
        }
    }
}
