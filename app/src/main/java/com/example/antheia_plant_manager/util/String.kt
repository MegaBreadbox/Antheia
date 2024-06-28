package com.example.antheia_plant_manager.util

import java.util.regex.Pattern

private const val PASSWORD_PATTERN = "^(?=.*\\p{Nd})(?=.*\\p{Ll})(?=.*\\p{Lu})(?=.*([^\\w\\s]|[_ ])).{8,500}"

fun String.validatePassword(): Boolean {
    return Pattern.compile(PASSWORD_PATTERN).matcher(this).matches()
}
