package ingameIME.context

import cglfw.glfwPollEvents
import cglfw.glfwSwapBuffers
import com.kgl.glfw.Glfw
import com.kgl.glfw.Window
import ingameIME.IngameIME
import kotlinx.coroutines.MainScope
import platform.windows.GetForegroundWindow
import platform.windows.WM_SETFOCUS
import kotlin.test.BeforeTest
import kotlin.test.Ignore
import kotlin.test.Test

class TestInputContext {
    private val scope = MainScope()
    private lateinit var window: Window
    private lateinit var context: InputContext

    @Suppress("DeferredResultUnused")
    @BeforeTest
    fun launchWindow() {
        scope.run {
            Glfw.init()
            context = InputContext(2000)
            window = Window(800, 600, "IngameIME").apply {
                this.setFocusCallback { _: Window, focused: Boolean ->
                    if (focused)
                        context.onWindowFocusMsg(GetForegroundWindow()!!, WM_SETFOCUS.toUInt())
                }
            }

            //PreEdit
            context.composition.preEdit.context.append { _, new ->
                println("PreEdit:${new.content}, Sel:${new.selection}")
                return@append new
            }

            //Commit
            context.composition.commit.append { _, new ->
                println("Commit:$new")
                return@append new
            }

            //CandidateList
            with(context.composition.candidateList) {
                context.append { _, new ->
                    with(new) {
                        if (displayRange == IntRange.EMPTY) println("Empty CandidateList")
                        for (i in displayRange) {
                            val candidate = content[i]
                            println("[${getDisplayIndex(candidate)}]$candidate")
                        }
                    }
                    return@append new
                }
            }

            //InputProcessor
            context.inputProcessor.append { _, new ->
                println("Cur Active:$new")
                return@append new
            }

            //Conversion
            context.conversionMode.append { _, new ->
                new.forEach(::println)
                return@append new
            }

            //Enable IM
            context.imState.setProperty(IngameIME.defaultAllowIM)

            while (!window.shouldClose) {
                glfwSwapBuffers(window.ptr)
                glfwPollEvents()
            }
        }
    }

    @Test
    @Ignore
    fun triggerRunTest() {
    }
}