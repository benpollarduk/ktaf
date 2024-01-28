# Conditional Descriptions

## Overview
Normally assets are assigned a **Description** during the constructor. This is what is returned when the asset is examined.

Descriptions are usually specified as a string.

```kotlin
val item = Item("The items name", "The items description.")
```

They can also be specified as a **Description**.

```kotlin
val item = Item(Identifier("The items name"), Description("The items description."))
```

However, sometimes it may be desirable to have a conditional description that can change based on the state of the asset.

Conditional descriptions can be specified with **ConditionalDescription** and contain a lambda which determines which one of two strings are returned when the asset is examined.

```kotlin
 // the player, just for demo purposes
val player = PlayableCharacter("Ben", "A man.")

// the description to use when the condition is true
val trueString = "A gleaming sword, owned by Ben."

// the string to use when the condition is false
val falseString = "A gleaming sword, without an owner."

// the conditional description itself
val conditionalDescription = ConditionalDescription(trueString, falseString) {
    player.findItem("Sword") != null
}

// create the item with the conditional description
val sword = Item(Identifier("Sword"), conditionalDescription)
```
