package com.shoeadda.app.feature.start.domain.usecase

import com.shoeadda.app.feature.start.domain.model.SupportedLanguages
import com.shoeadda.app.feature.start.domain.repository.LocaleController
import javax.inject.Inject

class GetDefaultLanguageCode @Inject constructor(
    private val localeController: LocaleController,
) {
    /** The phone's language if the app supports it, otherwise English. */
    operator fun invoke(): String {
        val device = localeController.deviceLanguageCode()
        return if (SupportedLanguages.isSupported(device)) device else SupportedLanguages.DEFAULT_CODE
    }
}
