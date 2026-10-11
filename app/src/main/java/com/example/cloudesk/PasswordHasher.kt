package com.example.cloudesk

import android.util.Base64
import java.security.MessageDigest
import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

/**
 * Salted PBKDF2 password hashing. Stored format:  pbkdf2$<iterations>$<salt>$<hash>
 * Old plain-text passwords (from before hashing was added) are still accepted by verify()
 * so nobody gets locked out; callers should re-save them hashed (see needsUpgrade()).
 */
object PasswordHasher {
    private const val PREFIX = "pbkdf2"
    private const val ITERATIONS = 10000
    private const val KEY_BITS = 256
    private const val ALGORITHM = "PBKDF2WithHmacSHA1"

    fun hash(plain: String): String {
        val salt = ByteArray(16).also { SecureRandom().nextBytes(it) }
        val hash = derive(plain, salt, ITERATIONS)
        return listOf(PREFIX, ITERATIONS.toString(), b64(salt), b64(hash)).joinToString("\$")
    }

    fun isHashed(stored: String): Boolean = stored.startsWith(PREFIX + "\$")

    fun needsUpgrade(stored: String): Boolean = !isHashed(stored)

    fun verify(plain: String, stored: String): Boolean {
        if (!isHashed(stored)) return plain == stored // legacy plain-text account
        val parts = stored.split("\$")
        if (parts.size != 4) return false
        return try {
            val iterations = parts[1].toInt()
            val salt = Base64.decode(parts[2], Base64.NO_WRAP)
            val expected = Base64.decode(parts[3], Base64.NO_WRAP)
            MessageDigest.isEqual(expected, derive(plain, salt, iterations))
        } catch (e: Exception) {
            false
        }
    }

    private fun derive(plain: String, salt: ByteArray, iterations: Int): ByteArray {
        val spec = PBEKeySpec(plain.toCharArray(), salt, iterations, KEY_BITS)
        return SecretKeyFactory.getInstance(ALGORITHM).generateSecret(spec).encoded
    }

    private fun b64(bytes: ByteArray): String = Base64.encodeToString(bytes, Base64.NO_WRAP)
}
