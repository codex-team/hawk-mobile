package so.codex.hawk

/**
 * Class containing application-level constants
 */
object AppData {
    /**
     * @property API_URL Constant containing url used by GraphQL API.
     */
    const val API_URL = "https://api.beta.hawk.so/graphql"

    /**
     * @property LOG_KEY_LOGGING_REQUESTS A constant containing a key for logging API requests.
     *                                    Used in LoggingInterceptor.
     */
    const val LOG_KEY_LOGGING_REQUESTS = "HAWK_REQUESTS_TO_API"

    /**
     * @property SIZE_LOG_FILE contains the size in bytes for one log file. Default value 1 mb.
     */
    const val SIZE_LOG_FILE = 1_048_576

    /**
     * @property LIMIT_LOG_FILE contains the number of files to write logs.
     */
    const val LIMIT_LOG_FILE = 3
}
