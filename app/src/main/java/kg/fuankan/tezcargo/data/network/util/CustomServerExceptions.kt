package kg.fuankan.tezcargo.data.network.util

import java.io.IOException

open class CustomServerException(open val description: String? = null, val errorCode: Int? = null) : IOException()

class ApiException(message: String): IOException(message)