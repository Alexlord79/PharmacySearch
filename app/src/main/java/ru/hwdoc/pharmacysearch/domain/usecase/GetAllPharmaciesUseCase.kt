package ru.hwdoc.pharmacysearch.domain.usecase

import ru.hwdoc.pharmacysearch.domain.repository.PharmacyRepository
import javax.inject.Inject

class GetAllPharmaciesUseCase @Inject constructor(
    private val pharmacyRepository: PharmacyRepository
) {

    operator fun invoke() = pharmacyRepository.getAllPharmacies()
}