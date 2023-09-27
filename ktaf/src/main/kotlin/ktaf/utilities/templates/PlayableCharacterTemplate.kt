package ktaf.utilities.templates

import ktaf.assets.characters.PlayableCharacter
import kotlin.reflect.KClass

/**
 * Provides a template for producing [PlayableCharacter] objects.
 */
public open class PlayableCharacterTemplate<TDerived : PlayableCharacterTemplate<TDerived>> {
    protected open fun onCreate(): PlayableCharacter {
        throw NotImplementedError()
    }

    protected companion object {
        protected inline fun <reified T : PlayableCharacterTemplate<T>> create(): PlayableCharacter {
            val clazz: KClass<T> = T::class
            return clazz.java.getDeclaredConstructor().newInstance().onCreate()
        }
    }
}
