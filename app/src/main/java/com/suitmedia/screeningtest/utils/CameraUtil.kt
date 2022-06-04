package com.suitmedia.screeningtest.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.util.*

object CameraUtil {
    private var capturedFileName: String = ""
    private var capturedFilePath: String = ""

    @Throws(IOException::class)
    fun createImageFile(context: Context): File {
        // Create an image file name
        val cal = Calendar.getInstance()
        val timeStamp = cal.timeInMillis
        val storageDir: File = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        Timber.e(storageDir.toString())
        val prefixFileName = "profile-${timeStamp}"
        capturedFileName = "${prefixFileName}.jpg"

        return File.createTempFile(
            prefixFileName, /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            capturedFilePath = absolutePath
        }
    }

    private fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            inContext.contentResolver,
            inImage,
            "Title",
            null
        )
        return Uri.parse(path)
    }
}