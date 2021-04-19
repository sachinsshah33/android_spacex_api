package extension.domain.spacex.data.network.exceptions

class UnknownException : Exception() {

    override val message: String?
        get() = "Some Unknown Error Occurred"
}