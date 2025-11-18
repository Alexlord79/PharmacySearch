package ru.hwdoc.pharmacysearch.domain.usecase

import javax.inject.Inject

class CopyToClipboardUseCase @Inject constructor() {

    operator fun invoke(copyInformation: String): String {
        return copyInformation.trim()
    }
}