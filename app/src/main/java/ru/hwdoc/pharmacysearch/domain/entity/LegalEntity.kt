package ru.hwdoc.pharmacysearch.domain.entity

data class LegalEntity(
    val id: Int = 0, // id юридического лица
    val fullName: String, // Полное наименование юридического лица
    val legalAddress: String, // Юридический адрес регистрации
    val inn: String, // Идентификационный номер налогоплательщика (ИНН)
    val ogrn: String, // Основной государственный регистрационный номер (ОГРН)
    val superintendent: String, // Ссылка на ID управляющего (может быть null)
    val basisOfAuthority: String // Документ, на основании которого действует юрлицо
)
