package ingameIME.context

typealias HWND = Int

/**
 * Create InputContext for the calling thread
 *
 * @param defaultFontHeight [ingameIME.context.composition.PreEdit.defaultFontHeight]
 *
 * @Note Context should be thread local
 * @Note A thread can create at most one context
 * @Note Once disposed, you can't recreate once more
 */
class InputContextWin32(defaultFontHeight: Int) : InputContext(defaultFontHeight) {
    init {
        initialize()
    }

    /**
     * Initialize Context
     */
    private external fun initialize()

    /**
     * It's required to call this method to set the correct input method state,
     * when the window received WM_SETFOCUS or WM_KILLFOCUS
     *
     * @param hWnd Win32 handle of the window
     * @param msg can be WM_SETFOCUS or WM_KILLFOCUS
     */
    external fun onWindowFocusMsg(hWnd: HWND, msg: UInt)
}