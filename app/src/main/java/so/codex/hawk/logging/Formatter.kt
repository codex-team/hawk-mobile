package so.codex.hawk.logging

interface Formatter {
    fun format(priority: Int, tag: String?, message: String):String
}