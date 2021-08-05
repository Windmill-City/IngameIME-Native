package ingameIME.context

/**
 * Listen for commit string
 */
interface ICommitListener {
    /**
     * Call from input method when commit
     *
     * @param commit the commit string
     */
    fun onCommit(commit: String)
}