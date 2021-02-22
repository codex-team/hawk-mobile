package so.codex.hawk.ui.main

import so.codex.hawk.entity.Event
import so.codex.hawk.entity.Project
import so.codex.hawk.entity.WorkspaceCut

/**
 * Debug object.
 */
object FakeRepository {
    private const val fakeImageUrlOne = "https://codex.so/public/app/img/external/codex2x.png"
    private const val fakeImageUrlTwo =
        "https://cdn.pixabay.com/photo/2015/07/30/11/04/bike-867229_960_720.jpg"
    private const val fakeImageUrlThree =
        "https://cdn.pixabay.com/photo/2018/01/25/16/18/pinterest-3d-3106488_960_720.jpg"

    fun getProjects(): List<Project> {
        return listOf(
            Project(
                "a",
                "Hawk",
                "TypeError: Cannot read property 'indexOf'. Use getProperty().",
                fakeImageUrlOne,
                7,
                Event()
            ),
            Project(
                "b",
                "Project B",
                "TypeError: Cannot read property 'indexOf'. Use getProperty().",
                "",
                77,
                Event()
            ),
            Project(
                "c",
                "Project C",
                "TypeError: Cannot read property 'indexOf'. Use getProperty().",
                fakeImageUrlTwo,
                777,
                Event()
            ),
            Project(
                "d",
                "Project D",
                "TypeError: Cannot read property 'indexOf'. Use getProperty().",
                fakeImageUrlThree,
                7777,
                Event()
            ),
            Project(
                "f",
                "Project F",
                "TypeError: Cannot read property 'indexOf'. Use getProperty().",
                "",
                77777,
                Event()
            ),
            Project(
                "1",
                "Project 1",
                "TypeError: Cannot read property 'indexOf'. Use getProperty().",
                fakeImageUrlOne,
                1234567,
                Event()
            ),
            Project(
                "5",
                "Project 5",
                "TypeError: Cannot read property 'indexOf'. Use getProperty().",
                "",
                1234567,
                Event()
            ),
            Project(
                "8",
                "Project 8",
                "TypeError: Cannot read property 'indexOf'. Use getProperty().",
                "",
                11,
                Event()
            )
        )
    }

    fun getWorkspaces(): List<WorkspaceCut> {
        return listOf(
            WorkspaceCut(
                "1",
                "Hawk workspace",
                fakeImageUrlOne,
                "something workspace",
                9
            ),
            WorkspaceCut(
                "f",
                "Hawk workspace",
                "",
                "something workspace",
                99
            ),
            WorkspaceCut(
                "a",
                "Hawk workspace",
                fakeImageUrlTwo,
                "something workspace",
                999
            ),
            WorkspaceCut(
                "9",
                "Hawk workspace",
                "",
                "something workspace",
                9999
            )
        )
    }
}