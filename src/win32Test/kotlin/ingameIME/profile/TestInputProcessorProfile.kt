package ingameIME.profile

import com.kgl.glfw.Glfw
import kotlinx.cinterop.*
import platform.win32.libtf.TF_PROFILETYPE_INPUTPROCESSOR
import platform.win32.libtf.TF_PROFILETYPE_KEYBOARDLAYOUT
import platform.win32.libtf.libtf_InputProcessorProfile_t
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertTrue

class TestLocale {
    @Test
    fun testGetName() {
        val locale: Locale = "zh-CN"
        locale.getName().apply(::println)
    }
}

class TestInputProcessorProfile {
    private lateinit var tipMicrosoftPinyin: InputProcessorProfile
    private lateinit var hklAmericanKeyboard: InputProcessorProfile

    init {
        Glfw.init()

        run {
            val profile: libtf_InputProcessorProfile_t = nativeHeap.alloc()
            profile.dwProfileType = TF_PROFILETYPE_INPUTPROCESSOR.toUInt()

            //zh-CN
            profile.langid = 2052.toUShort()

            //CLSID of Microsoft Pinyin
            profile.clsid.Data1 = 0x81d4e9c9.toUInt()
            profile.clsid.Data2 = 0x1d3b.toUShort()
            profile.clsid.Data3 = 0x41bc.toUShort()
            profile.clsid.Data4[0] = 0x9e.toUByte()
            profile.clsid.Data4[1] = 0x6c.toUByte()
            profile.clsid.Data4[2] = 0x4b.toUByte()
            profile.clsid.Data4[3] = 0x40.toUByte()
            profile.clsid.Data4[4] = 0xbf.toUByte()
            profile.clsid.Data4[5] = 0x79.toUByte()
            profile.clsid.Data4[6] = 0xe3.toUByte()
            profile.clsid.Data4[7] = 0x5e.toUByte()

            //GUID Profile of Microsoft Pinyin
            profile.guidProfile.Data1 = 0xfa550b04.toUInt()
            profile.guidProfile.Data2 = 0x5ad7.toUShort()
            profile.guidProfile.Data3 = 0x411f.toUShort()
            profile.guidProfile.Data4[0] = 0xa5.toUByte()
            profile.guidProfile.Data4[1] = 0xac.toUByte()
            profile.guidProfile.Data4[2] = 0xca.toUByte()
            profile.guidProfile.Data4[3] = 0x03.toUByte()
            profile.guidProfile.Data4[4] = 0x8e.toUByte()
            profile.guidProfile.Data4[5] = 0xc5.toUByte()
            profile.guidProfile.Data4[6] = 0x15.toUByte()
            profile.guidProfile.Data4[7] = 0xd7.toUByte()

            tipMicrosoftPinyin = TextService(profile)
        }
        run {
            val profile: libtf_InputProcessorProfile_t = nativeHeap.alloc()
            profile.dwProfileType = TF_PROFILETYPE_KEYBOARDLAYOUT.toUInt()

            //en-US
            profile.langid = 1033.toUShort()

            //HKL of American Keyboard
            profile.hkl = 0x0409_0409.toLong().toCPointer()

            hklAmericanKeyboard = KeyboardLayout(profile)
        }
    }

    @Test
    fun testGetName() {
        tipMicrosoftPinyin.name.apply(::println)
        hklAmericanKeyboard.name.apply(::println)
    }

    @Test
    fun testGetLocale() {
        tipMicrosoftPinyin.locale.apply(::println)
        hklAmericanKeyboard.locale.apply(::println)
    }

    @Test
    fun testToString() {
        tipMicrosoftPinyin.toString().apply(::println)
        hklAmericanKeyboard.toString().apply(::println)
    }

    @Test
    fun testHashCode() {
        assertEquals(-672159701, tipMicrosoftPinyin.hashCode())
        assertEquals(67699721, hklAmericanKeyboard.hashCode())
    }

    @Test
    fun testDispose() {
        assertTrue { !tipMicrosoftPinyin.disposed }
        assertFails { tipMicrosoftPinyin.disposed = false }
        tipMicrosoftPinyin.disposed = true
        assertTrue { tipMicrosoftPinyin.disposed }
    }
}