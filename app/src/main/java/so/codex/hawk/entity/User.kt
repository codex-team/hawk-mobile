package so.codex.hawk.entity

/**
 * Class which represents user profile
 * @property id an id of [User]
 * @property name name of [User]
 * @property email an email of [User]
 * @property image avatar of [User]
 */
data class User(
    var id: String? = "",
    var name: String? = "",
    var email: String? = "",
    var image: String? = ""
)
