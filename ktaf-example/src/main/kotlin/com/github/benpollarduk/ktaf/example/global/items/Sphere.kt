package com.github.benpollarduk.ktaf.example.global.items

import com.github.benpollarduk.ktaf.assets.Item
import com.github.benpollarduk.ktaf.assets.characters.PlayableCharacter
import com.github.benpollarduk.ktaf.assets.interaction.Reaction
import com.github.benpollarduk.ktaf.assets.interaction.ReactionResult
import com.github.benpollarduk.ktaf.assets.locations.Overworld
import com.github.benpollarduk.ktaf.assets.locations.Region
import com.github.benpollarduk.ktaf.assets.locations.Room
import com.github.benpollarduk.ktaf.commands.CustomCommand
import com.github.benpollarduk.ktaf.interpretation.CommandHelp
import com.github.benpollarduk.ktaf.utilities.templates.ItemTemplate

internal class Sphere(
    private val region: Region,
    private val overworld: Overworld
) : ItemTemplate() {
    override fun instantiate(playableCharacter: PlayableCharacter, room: Room?): Item {
        return Item(
            "${region.identifier.name} Sphere",
            "A glass sphere, about the size of a snooker ball. Inside you can see a swirling mist.",
            true
        ).also {
            it.commands = listOf(
                CustomCommand(
                    CommandHelp(
                        "Warp ${region.identifier.name}",
                        "Use the ${region.identifier.name} Sphere to warp to the " +
                            "${region.identifier.name}."
                    ),
                    true
                ) { game, _ ->
                    val move = overworld.move(region)
                    if (!move) {
                        Reaction(
                            ReactionResult.ERROR,
                            "Could not move to ${region.identifier.name}."
                        )
                    }
                    game.displayTransition(
                        "",
                        "You peer inside the sphere and feel faint. When the sensation passes you open " +
                            "you eyes and have been transported to the ${region.identifier.name}."
                    )

                    Reaction(ReactionResult.INTERNAL, "")
                }
            )
        }
    }
}
