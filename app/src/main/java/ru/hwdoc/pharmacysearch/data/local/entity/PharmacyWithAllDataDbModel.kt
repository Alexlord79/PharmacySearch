package ru.hwdoc.pharmacysearch.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class PharmacyWithAllDataDbModel(
    @Embedded
    val pharmacy: PharmacyDbModel,
    @Relation(
        parentColumn = "pharmacy_manageress_id",
        entityColumn = "id"
    )
    val pharmacyManageress: PersonDbModel,                  //заведка
    @Relation(
        parentColumn = "director_of_macroregion_id",
        entityColumn = "id"
    )
    val directorOfMacroregion: PersonDbModel,             //руководитель макрорегиона
    @Relation(
        parentColumn = "head_of_the_regional_id",
        entityColumn = "id"
    )
    val headOfTheRegional: PersonDbModel,                 //руководителm региона
    @Relation(
        parentColumn = "manager_id",
        entityColumn = "id"
    )
    val manager: PersonDbModel,                           //управляющий/завхоз
    @Relation(
        parentColumn = "legal_entity_id",
        entityColumn = "id"
    )
    val legalEntity: LegalEntityDbModel,                  //юр. лицо
    @Relation(
        parentColumn = "vsa_id",
        entityColumn = "id"
    )
    val vsa: VsaDbModel,                                  // ВСА
    @Relation(
        parentColumn = "internet_provider_id",
        entityColumn = "id"
    )
    val internetProvider: InternetProviderDbModel         //интернет провайдер
)
