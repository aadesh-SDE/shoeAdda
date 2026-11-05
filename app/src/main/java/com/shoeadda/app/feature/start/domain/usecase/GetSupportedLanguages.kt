package com.shoeadda.app.feature.start.domain.usecase

import com.shoeadda.app.feature.start.domain.model.AppLanguage
import com.shoeadda.app.feature.start.domain.model.SupportedLanguages
import javax.inject.Inject

class GetSupportedLanguages @Inject constructor() {
    operator fun invoke(): List<AppLanguage> = SupportedLanguages.all
}
