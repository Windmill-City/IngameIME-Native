package ingameIME.context

/**
 * Listen for commit string
 */
interface ICommitListener {
    /**
     * Call when input method commit
     *
     * @param commit the commit string
     */
    fun onCommit(commit: String)
}