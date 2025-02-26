package dev.gbenga.endurely.core


fun isValidEmail(email: String): Boolean {
    val parts = email.trim().lowercase().split("@")
    if (parts.size != 2 || parts[0].isEmpty() || !parts[0][0].isLetter()) return false

    val domainParts = parts[1].split(".")
    return domainParts.size in 2..3 && domainParts.all { it.isNotEmpty() && it.all(Char::isLetter) }
}
