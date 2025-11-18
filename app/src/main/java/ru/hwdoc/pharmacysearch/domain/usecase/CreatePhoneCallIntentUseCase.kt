package ru.hwdoc.pharmacysearch.domain.usecase

import android.content.Intent
import androidx.core.net.toUri
import javax.inject.Inject

class CreatePhoneCallIntentUseCase @Inject constructor() {

    operator fun invoke(phoneNumber: String): Intent {
        return Intent(Intent.ACTION_DIAL).apply {
            data = "tel:$phoneNumber".toUri()
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
    }
}