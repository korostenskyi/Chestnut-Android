package io.korostenskyi.chestnut.domain.model

data class BuildParams(
    val isDebug: Boolean,
    val tmdbApiBaseUrl: String,
    val tmdbApiKey: String
)
