package com.github.nenadjakic.eav.util

enum class Message(
    private val messagePattern: String
) {
    MESSAGE_EXAMPLE_001("User friendly message. {0} - {1}");

    fun formatMessage(vararg parameters: Any?): String {
        return messagePattern.format(parameters)
    }
}