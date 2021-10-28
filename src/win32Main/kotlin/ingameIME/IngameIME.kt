package ingameIME

import ingameIME.context.inputState.IInputState
import ingameIME.context.inputState.imState.AllowIM
import ingameIME.context.inputState.imState.ForbidIM
import ingameIME.context.inputState.imState.IAllowIM
import ingameIME.context.inputState.imState.IForbidIM
import ingameIME.context.inputState.inputMode.conversion.*
import ingameIME.context.inputState.inputMode.conversion.jap.HiraganaMode
import ingameIME.context.inputState.inputMode.conversion.jap.KatakanaMode
import ingameIME.context.inputState.inputMode.conversion.jap.RomanMode
import ingameIME.profile.IInputProcessorProfile
import ingameIME.profile.toWrappedProfile
import ingameIME.win32.succeedOrThr
import kotlinx.cinterop.*
import platform.posix.uint32_tVar
import platform.win32.libtf.libtf_InputProcessorProfile_t
import platform.win32.libtf.libtf_get_input_processors

object IngameIME : IIngameIME {
    /**
     * System available input processors
     */
    override val availableInputProcessors: List<IInputProcessorProfile>
        get() {
            memScoped {
                //Get data size first
                val fetched: uint32_tVar = this.alloc()
                libtf_get_input_processors(null, 0, fetched.ptr).succeedOrThr("Getting InputProcessor count")

                //Alloc buffer and get data
                val profiles = this.allocArray<libtf_InputProcessorProfile_t>(fetched.value.toInt())
                libtf_get_input_processors(
                    profiles,
                    fetched.value,
                    fetched.ptr
                ).succeedOrThr("Fetching InputProcessor data")

                //Store data
                val result = mutableListOf<IInputProcessorProfile>()
                for (i in 0 until fetched.value.toInt())
                    result.add(profiles[i].toWrappedProfile())
                return result
            }
        }

    /**
     * System available input states
     *
     * @see ingameIME.context.inputState
     */
    override val availableInputStates: List<IInputState>
        get() = listOf(
            AllowIM,
            ForbidIM,
            AlphaNumericMode,
            NativeMode,
            HalfShapeMode,
            FullShapeMode,
            RomanMode,
            KatakanaMode,
            HiraganaMode
        )

    /**
     * Default IM state that can enable input method
     */
    override val defaultAllowIM: IAllowIM
        get() = AllowIM

    /**
     * Default IM state that can disable input method
     */
    override val defaultForbidIM: IForbidIM
        get() = ForbidIM

    /**
     * Default Conversion Mode that sets [IAlphaNumericMode]
     */
    override val defaultAlphaNumericMode: IAlphaNumericMode
        get() = AlphaNumericMode

    /**
     * Default Conversion Mode that sets [INativeMode]
     */
    override val defaultNativeMode: INativeMode
        get() = NativeMode
}