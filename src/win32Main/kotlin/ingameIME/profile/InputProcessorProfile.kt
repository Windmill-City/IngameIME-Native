package ingameIME.profile

import ingameIME.win32.*
import kotlinx.cinterop.*
import platform.win32.libtf.libtf_InputProcessorProfile_t
import platform.win32.libtf.libtf_get_input_processor_desc
import platform.win32.libtf.libtf_get_input_processor_locale
import platform.win32.libtf.libtf_get_locale_name
import platform.windows.BSTRVar

/**
 * Return the localized name of the locale
 */
actual fun Locale.getName(): String {
    memScoped {
        val name: BSTRVar = this.alloc()
        this@getName.withBSTR {
            libtf_get_locale_name(it, name.ptr).succeedOrThr()
        }
        return name.toKString()
    }
}

abstract class InputProcessorProfile(val nativeProfile: libtf_InputProcessorProfile_t) : IInputProcessorProfile {
    /**
     * Locale of the profile
     */
    override val locale: Locale
        get() {
            memScoped {
                val locale: BSTRVar = this.alloc()
                libtf_get_input_processor_locale(nativeProfile.readValue(), locale.ptr).succeedOrThr()
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
                libtf_get_input_processor_desc(nativeProfile.readValue(), name.ptr).succeedOrThr()
                return name.toKString()
            }
        }

    /**
     * Set to true to dispose the native handle
     */
    override var disposed: Boolean = false
        set(value) {
            if (!value) throw Error("Set false to dispose")
            nativeHeap.free(nativeProfile)
            field = true
        }
}

class KeyboardLayout(nativeProfile: libtf_InputProcessorProfile_t) :
    InputProcessorProfile(nativeProfile), IKeyBoardLayout {
    override fun toString(): String {
        return "InputProcessor[Win32][HKL][${
            nativeProfile.hkl.toLong().toFormattedString()
        }][$locale][${locale.getName()}]:$name"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is InputProcessorProfile) return false
        return other.nativeProfile.hkl.toLong() == this.nativeProfile.hkl.toLong()
    }

    override fun hashCode(): Int {
        return nativeProfile.hkl.toLong().hashCode()
    }
}

class TextService(nativeProfile: libtf_InputProcessorProfile_t) :
    InputProcessorProfile(nativeProfile), IInputMethodProfile {
    override fun toString(): String {
        return "InputProcessor[Win32][TIP][${nativeProfile.clsid.toFormattedString()}][$locale][${locale.getName()}]:$name"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is InputProcessorProfile) return false
        return other.nativeProfile.clsid.isEqual(this.nativeProfile.clsid)
    }

    override fun hashCode(): Int {
        return nativeProfile.clsid.getHashCode()
    }
}