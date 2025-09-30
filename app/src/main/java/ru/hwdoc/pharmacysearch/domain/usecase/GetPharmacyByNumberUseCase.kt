package ru.hwdoc.pharmacysearch.domain.usecase

import ru.hwdoc.pharmacysearch.domain.repository.PharmacyRepository
import javax.inject.Inject

class GetPharmacyByNumberUseCase @Inject constructor(
    private val repository: PharmacyRepository
) {

    suspend operator fun invoke(number: Int) = repository.getPharmacyByNumber(number)
}