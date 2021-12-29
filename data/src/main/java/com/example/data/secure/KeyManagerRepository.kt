package com.example.data.secure

import android.content.Context
import android.os.Build
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKey
import net.sqlcipher.database.SupportFactory
import java.io.File
import java.security.SecureRandom
import javax.crypto.KeyGenerator

class KeyManagerRepository(private val context: Context) {

    private val fileName = "passphrase.bin"
    private val keyLength = 256
    private val algorithm = "HmacSHA256"

    private fun getPassphrase(): ByteArray {
        val file = File(context.filesDir, fileName)
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
        val encryptedFile = EncryptedFile.Builder(
            context,
            file,
            masterKey,
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
        ).build()

        return if (file.exists()) {
            encryptedFile.openFileInput().use { it.readBytes() }
        } else {
            generatePassphrase().also { passphrase ->
                encryptedFile.openFileOutput().use { it.write(passphrase) }
            }
        }
    }

    private fun generatePassphrase(): ByteArray {
        // Generate a 256-bit key
        val outputKeyLength = keyLength

        val secureRandom = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            SecureRandom.getInstanceStrong()
        } else {
            // Do *not* seed secureRandom! Automatically seeded from system entropy.
            SecureRandom()
        }
        val keyGenerator: KeyGenerator = KeyGenerator.getInstance(algorithm)
        keyGenerator.init(outputKeyLength, secureRandom)

        var tempArray = keyGenerator.generateKey().encoded

        // filter out zero byte values, as SQLCipher does not like them
        while (tempArray.contains(0)) {
            tempArray = keyGenerator.generateKey().encoded
        }

        return tempArray
    }

    fun getCypherFactory(): SupportFactory {
        val secure = KeyManagerRepository(context).getPassphrase()
        return SupportFactory(secure)
    }
}
