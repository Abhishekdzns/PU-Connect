package com.example.puconnect.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.ByteArrayOutputStream

class Compressor {
    fun uriToCompressedBitmap(context: Context, uri: Uri): ByteArray {
        val pfd = context.contentResolver.openFileDescriptor(uri, "r")
        val bitmap =
            BitmapFactory.decodeFileDescriptor(pfd?.fileDescriptor, null, null)
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos)
        pfd?.close()
        return baos.toByteArray()
    }
}