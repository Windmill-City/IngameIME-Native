package ingameIME.profile

import ingameIME.win32.*
import kotlinx.cinterop.*
import platform.posix.memcpy
import platform.win32.libtf.*
import platform.windows.BSTRVar

/**
 * Return the localized name of the locale
 */
actual fun Locale.getName(): String {
    memScoped {
        val name: BSTRVar = this.alloc()
        this@getName.useBSTR {
            libtf_get_locale_name(it, name.ptr).succeedOrThr()
        }
        return name.toKString()
    }
}

abstract class InputProcessorProfile(val nativeProfile: CValue<libtf_InputProcessorProfile_t>) :
    IInputProcessorProfile {
    /**
     * Locale of the profile
     */
    override val locale: Locale
        get() {
            memScoped {
                val locale: BSTRVar = this.alloc()
                libtf_get_input_processor_locale(nativeProfile, locale.ptr).succeedOrThr()
                return locale.toKString()
            }
        }

    /**
     * The localized name of the profile
     */
    override val name: String
        get() {
            memScoped {
                val name: BSTRVar = this.alloc()
                libtf_get_input_processor_desc(nativeProfile, name.ptr).succeedOrThr()
                return name.toKString()
            }
        }
}

class KeyboardLayout(nativeProfile: CValue<libtf_InputProcessorProfile_t>) :
    InputProcessorProfile(nativeProfile), IKeyBoardLayout {
    override fun toString(): String {
        return "InputProcessor[Win32][HKL][${
            nativeProfile.useContents { hkl.toLong().toFormattedString() }
        }][$locale][${locale.getName()}]:$name"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is InputProcessorProfile) return false
        return other.nativeProfile.useContents { hkl.toLong() } == this.nativeProfile.useContents { hkl.toLong() }
    }

    override fun hashCode(): Int {
        return nativeProfile.useContents { hkl.toLong().hashCode() }
    }
}

class TextService(nativeProfile: CValue<libtf_InputProcessorProfile_t>) :
    InputProcessorProfile(nativeProfile), IInputMethodProfile {
    override fun toString(): String {
        return "InputProcessor[Win32][TIP][${nativeProfile.useContents { clsid.toFormattedString() }}][$locale][${locale.getName()}]:$name"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is InputProcessorProfile) return false
        return other.nativeProfile.useContents { clsid }.isEqual(this.nativeProfile.useContents { clsid })
    }

    override fun hashCode(): Int {
        return nativeProfile.useContents { clsid.getHashCode() }
    }
}

/**
 * Convert native profile to wrapped profile base on its type
 */
fun libtf_InputProcessorProfile_t.toWrappedProfile(): InputProcessorProfile {
    with(cValue<libtf_InputProcessorProfile_t> {
        memcpy(this.ptr, this@toWrappedProfile.ptr, sizeOf<libtf_InputProcessorProfile_t>().toULong())
    }) {
        return when (profileType) {
            TF_PROFILETYPE_INPUTPROCESSOR.toUInt() -> TextService(this)
            TF_PROFILETYPE_KEYBOARDLAYOUT.toUInt() -> KeyboardLayout(this)
            else -> throw Error("Unsupported profile type:$profileType")
        }
    }
}
