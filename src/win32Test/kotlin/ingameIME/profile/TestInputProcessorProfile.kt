package ingameIME.profile

import ingameIME.IngameIME
import kotlin.test.Test

class TestLocale {
    @Test
    fun testGetName() {
        val locale: Locale = "zh-CN"
        locale.getName().apply(::println)
    }
}

class TestInputProcessorProfile {
    init {
        IngameIME.availableInputProcessors[0]
    }

    @Test
    fun testGetName() {
    }

    @Test
    fun testGetLocale() {
    }

    @Test
    fun testEqual() {
    }
}
