# Attributes

## Overview
All examinable objects can have attributes. Attributes provide a way of adding a lot of depth to games. For example, 
attributes could be used to buy and sell items, contain a characters XP or HP or even provide a way to add durability 
to items.

## Use
To add to an existing attribute or to create a new one use the **add** function.

```kotlin
var player = PlayableCharacter("Player", "")
player.attributes.add("$", 10)
```

To subtract from an existing attribute use the **subtract** function.

```kotlin
player.attributes.subtract("$", 10)
```

Attributes values can be capped. In this example the $ attribute is limited to a range of 0 - 100. Adding or 
subtracting will not cause the value of the attribute to change outside of this range.

```kotlin
var cappedAttribute = Attribute("$", "Dollars.", 0, 100)
player.attributes.add(cappedAttribute, 50)
```

## An example - buying an Item from a NonPlayableCharacter.
The following is an example of buying an Item from NonPlayableCharacter. Here a trader has a spade. The player can only
buy the spade if they have at least $5. The conversation will jump to the correct paragraph based on if they choose to 
buy the spade or not. If the player chooses to buy the spade and has enough $ the transaction is made and the spade 
changes hands.

```kotlin
val currency = "$"
val spade = Item("Spade", "")

val player = PlayableCharacter("Player", "").apply {
    attributes.add(currency, 10)
}

val trader = NonPlayableCharacter("Trader", "").apply {
    acquireItem(spade)
    conversation = Conversation(
        listOf(
            Paragraph("What will you buy").also {
                it.responses = listOf(
                    Response("Spade.", ByCallback {
                        if (player.attributes.getValue(currency) >= 5) {
                            ToName("BoughtSpade")
                        } else {
                            ToName("NotEnough")
                        }
                    }),
                    Response("Nothing.", Last()),
                )
            },
            Paragraph("Here it is.", First(), "BoughtSpade") {
                player.attributes.subtract(currency, 5)
                attributes.add(currency, 5)
                give(spade, player)
            },
            Paragraph("You don't have enough money.", First(), "NotEnough"),
            Paragraph("Fine.")
        ),
    )
}
```
This is just one example of using attributes to add depth to a game.