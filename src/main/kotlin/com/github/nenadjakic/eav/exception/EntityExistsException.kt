package com.github.nenadjakic.eav.exception

class EntityExistsException : Exception {
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
}