package ingameIME.win32

import platform.windows.E_FAIL
import platform.windows.S_OK
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertTrue

class TestKHRESULT {
    @Test
    fun testSucceedAndFailed() {
        assertTrue { E_FAIL.failed }
        assertTrue { S_OK.succeed }

        assertTrue { !E_FAIL.succeed }
        assertTrue { !S_OK.failed }
    }

    @Test
    fun testIfCondition() {
        run {
            var calls = 0
            S_OK.ifSucceed { calls++ }
            E_FAIL.ifFailed { calls++ }
            assertEquals(2, calls)
        }
        run {
            var calls = 0
            S_OK.ifFailed { calls++ }
            E_FAIL.ifSucceed { calls++ }
            assertEquals(0, calls)
        }
        run {
            var calls = 0
            assertEquals("Test->HRESULT:$E_FAIL", assertFails { E_FAIL.succeedOrThr("Test") }.message)
            S_OK.succeedOrThr("Test")
            S_OK.ifSucceedOrThr("Test") { calls++ }
            assertFails { E_FAIL.ifSucceedOrThr("Test") { calls++ } }
            assertEquals(1, calls)
        }
    }
}