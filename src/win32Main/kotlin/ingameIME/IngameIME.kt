package ingameIME

import ingameIME.context.inputState.IInputState
import ingameIME.context.inputState.imState.IAllowIM
import ingameIME.context.inputState.imState.IForbidIM
import ingameIME.context.inputState.inputMode.conversion.IAlphaNumericMode
import ingameIME.context.inputState.inputMode.conversion.INativeMode
import ingameIME.profile.IInputProcessorProfile
import ingameIME.profile.toWrappedProfile
import ingameIME.win32.succeedOrThr
import kotlinx.cinterop.*
import platform.posix.memcpy
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
                libtf_get_input_processors(null, 0, fetched.ptr).succeedOrThr()

                //Alloc buffer and get data
                val profiles = this.allocArray<libtf_InputProcessorProfile_t>(fetched.value.toInt())
                libtf_get_input_processors(profiles, fetched.value, fetched.ptr).succeedOrThr()

                //Store data
                val result = mutableListOf<IInputProcessorProfile>()
                for (i in 0 until fetched.value.toInt())
                    profiles[i].apply {
                        val profile: libtf_InputProcessorProfile_t = nativeHeap.alloc()
                        memcpy(profile.ptr, profiles[i].ptr, sizeOf<libtf_InputProcessorProfile_t>().toULong())
                        result.add(profile.toWrappedProfile())
                    }
                return result
            }
        }

    /**
     * System available input states
     *
     * @see ingameIME.context.inputState
     */
    override val availableInputStates: List<IInputState>
        get() = TODO("Not yet implemented")

    /**
     * Default IM state that can enable input method
     */
    override val defaultAllowIM: IAllowIM
        get() = TODO("Not yet implemented")

    /**
     * Default IM state that can disable input method
     */
    override val defaultForbidIM: IForbidIM
        get() = TODO("Not yet implemented")

    /**
     * Default Conversion Mode that sets [IAlphaNumericMode]
     */
    override val defaultAlphaNumericMode: IAlphaNumericMode
        get() = TODO("Not yet implemented")

    /**
     * Default Conversion Mode that sets [INativeMode]
     */
    override val defaultNativeMode: INativeMode
        get() = TODO("Not yet implemented")
}