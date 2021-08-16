package ingameIME.context.inputState.inputMode.sentence

import ingameIME.context.inputState.IInputState

/**
 * Sentence mode of the context
 */
interface ISentenceMode : IInputState

/**
 * Fallback value of [ISentenceMode]
 */
object UnknownSentenceMode : ISentenceMode {
    /**
     * Always conflicts with others, so it can be removed when applying new states
     */
    override fun accept(state: IInputState): Boolean {
        return false
    }
}
