package so.codex.hawk.ui.main

import so.codex.hawk.custom.views.badge.UiBadgeViewModel
import so.codex.hawk.ui.data.UiProject
import so.codex.hawk.utils.ShortNumberUtils

/**
 * Debug object.
 */
object FakeRepository {

    fun getUiProjects(): List<UiProject> {
        return listOf(
            UiProject(
                "y",
                "y",
                "Task :app:compileDebugJavaWithJavac UP-TO-DATE",
                "",
                7.toBadge(),
            ),
            UiProject(
                "a",
                "a",
                "Task :app:compileDebugJavaWithJavac UP-TO-DATE",
                "https://codex.so/public/app/img/external/codex2x.png",
                0.toBadge(),
            ),
            UiProject(
                "b",
                "b",
                "Task :app:compileDebugJavaWithJavac UP-TO-DATE",
                "https://codex.so/public/app/img/external/codex2x.png",
                77.toBadge(),
            ),
            UiProject(
                "c",
                "c",
                "Task :app:compileDebugJavaWithJavac UP-TO-DATE",
                "https://codex.so/public/app/img/external/codex2x.png",
                777.toBadge(),
            ),
            UiProject(
                "d",
                "d",
                "Task :app:compileDebugJavaWithJavac UP-TO-DATE",
                "https://codex.so/public/app/img/external/codex2x.png",
                7777.toBadge(),
            ),
            UiProject(
                "a",
                "a",
                "Task :app:compileDebugJavaWithJavac UP-TO-DATE",
                "",
                7.toBadge(),
            ),
            UiProject(
                "a",
                "a",
                "Task :app:compileDebugJavaWithJavac UP-TO-DATE",
                "",
                777777.toBadge(),
            ),
            UiProject(
                "a",
                "a",
                "Task :app:compileDebugJavaWithJavac UP-TO-DATE",
                "https://codex.so/public/app/img/external/codex2x.png",
                12345776.toBadge(),
            ),
            UiProject(
                "e",
                "e",
                "Task :app:compileDebugJavaWithJavac UP-TO-DATE",
                "",
                72346.toBadge(),
            ),
            UiProject(
                "7",
                "a",
                "Task :app:compileDebugJavaWithJavac UP-TO-DATE",
                "",
                7.toBadge(),
            ),
            UiProject(
                "q",
                "a",
                "Task :app:compileDebugJavaWithJavac UP-TO-DATE",
                "",
                7.toBadge(),
            ),
            UiProject(
                "a",
                "a",
                "Task :app:compileDebugJavaWithJavac UP-TO-DATE",
                "https://codex.so/public/app/img/external/codex2x.png",
                7.toBadge(),
            ),
            UiProject(
                "a",
                "a",
                "Task :app:compileDebugJavaWithJavac UP-TO-DATE",
                "https://codex.so/public/app/img/external/codex2x.png",
                7.toBadge(),
            ),
            UiProject(
                "a",
                "a",
                "Task :app:compileDebugJavaWithJavac UP-TO-DATE",
                "https://codex.so/public/app/img/external/codex2x.png",
                7.toBadge(),
            ),
            UiProject(
                "a",
                "a",
                "Task :app:compileDebugJavaWithJavac UP-TO-DATE",
                "https://codex.so/public/app/img/external/codex2x.png",
                7.toBadge(),
            ),
            UiProject(
                "a",
                "a",
                "Task :app:compileDebugJavaWithJavac UP-TO-DATE",
                "https://codex.so/public/app/img/external/codex2x.png",
                7.toBadge(),
            ),
            UiProject(
                "a",
                "a",
                "Task :app:compileDebugJavaWithJavac UP-TO-DATE",
                "https://codex.so/public/app/img/external/codex2x.png",
                7.toBadge(),
            ),
            UiProject(
                "a",
                "a",
                "Task :app:compileDebugJavaWithJavac UP-TO-DATE",
                "https://codex.so/public/app/img/external/codex2x.png",
                7.toBadge(),
            ),
            UiProject(
                "a",
                "a",
                "Task :app:compileDebugJavaWithJavac UP-TO-DATE",
                "https://codex.so/public/app/img/external/codex2x.png",
                7.toBadge(),
            ),
            UiProject(
                "a",
                "a",
                "Task :app:compileDebugJavaWithJavac UP-TO-DATE",
                "https://codex.so/public/app/img/external/codex2x.png",
                7.toBadge(),
            )
        )
    }

    private fun Int.toBadge(): UiBadgeViewModel {
        return UiBadgeViewModel(
            ShortNumberUtils.convert(this.toLong()),
            this.toLong()
        )
    }
}