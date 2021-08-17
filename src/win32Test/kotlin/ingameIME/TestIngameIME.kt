package ingameIME

import com.kgl.glfw.Glfw
import kotlin.test.Test

class TestIngameIME {
    init {
        Glfw.init()
    }

    @Test
    fun testAvailableInputProcessors() {
        IngameIME.availableInputProcessors.forEach { it.toString().apply(::println) }
    }
}