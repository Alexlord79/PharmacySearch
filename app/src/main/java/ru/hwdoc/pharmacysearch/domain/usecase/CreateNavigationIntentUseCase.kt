package ru.hwdoc.pharmacysearch.domain.usecase

import android.content.Intent
import androidx.core.net.toUri
import android.net.Uri
import javax.inject.Inject

class CreateNavigationIntentUseCase @Inject constructor() {

    operator fun invoke(yandexMapsLink: String): Intent {
        return Intent(Intent.ACTION_VIEW).apply {
            data = yandexMapsLink.containsHttps().toUri()
            println(data)
        }
    }
    operator fun invoke(locality: String, address: String): Intent {
        return Intent(Intent.ACTION_VIEW).apply {
            data = "geo:0,0?q=${Uri.encode("$locality $address")}".toUri()
            println(data)
        }
    }

    private fun String.containsHttps(): String {
        return if (this.lowercase().startsWith("https://")) {
            this
        } else if(this.lowercase().startsWith("http://")) {
            this.replace("http://","https://")
        } else {
            "https://${this}"
        }
    }
}