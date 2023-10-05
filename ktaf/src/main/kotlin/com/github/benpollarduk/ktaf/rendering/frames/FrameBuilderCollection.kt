package com.github.benpollarduk.ktaf.rendering.frames

/**
 * Provides a collection of frame builders.
 */
public data class FrameBuilderCollection(
    public val titleFrameBuilder: TitleFrameBuilder,
    public val aboutFrameBuilder: AboutFrameBuilder,
    public val helpFrameBuilder: HelpFrameBuilder,
    public val transitionFrameBuilder: TransitionFrameBuilder,
    public val completionFrameBuilder: CompletionFrameBuilder,
    public val gameOverFrameBuilder: GameOverFrameBuilder,
    public val conversationFrameBuilder: ConversationFrameBuilder,
    public val sceneFrameBuilder: SceneFrameBuilder,
    public val regionMapFrameBuilder: RegionMapFrameBuilder
)
