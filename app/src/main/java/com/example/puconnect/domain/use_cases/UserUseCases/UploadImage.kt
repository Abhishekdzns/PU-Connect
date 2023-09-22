package com.example.puconnect.domain.use_cases.UserUseCases

import android.net.Uri
import com.example.puconnect.domain.repository.UserRepository
import javax.inject.Inject

class UploadImage @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(userId: String, imageUri: ByteArray) = repository.uploadImage(userId, imageUri)
}