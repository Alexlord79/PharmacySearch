package ru.hwdoc.pharmacysearch.domain.usecase

import ru.hwdoc.pharmacysearch.domain.entity.PharmacyFilters
import ru.hwdoc.pharmacysearch.domain.repository.PharmacyRepository
import javax.inject.Inject

data class SearchPharmacyUseCase @Inject constructor(
    val repository: PharmacyRepository
){

    operator fun invoke(filters: PharmacyFilters) = repository.searchPharmacy(filters)
}
