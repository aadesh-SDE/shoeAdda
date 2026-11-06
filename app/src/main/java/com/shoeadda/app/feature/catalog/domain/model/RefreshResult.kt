package com.shoeadda.app.feature.catalog.domain.model

/** Outcome of loading the catalog. The bundled shoes always load; only the online ones can fail. */
data class RefreshResult(
    val bundledCount: Int,
    val onlineCount: Int,
    val onlineFailed: Boolean,
)
