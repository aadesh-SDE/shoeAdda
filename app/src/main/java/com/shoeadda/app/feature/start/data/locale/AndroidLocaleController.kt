package com.shoeadda.app.feature.start.data.locale

import android.content.res.Resources
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.shoeadda.app.feature.start.domain.model.SupportedLanguages
import com.shoeadda.app.feature.start.domain.repository.LocaleController
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/** Uses AndroidX per-app language support. On Android 12 and older the choice is stored by AppCompat itself. */
class AndroidLocaleController @Inject constructor() : LocaleController {

    override suspend fun applyLanguage(languageCode: String) {
        withContext(Dispatchers.Main.immediate) {
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(languageCode))
        }
    }

    override fun deviceLanguageCode(): String =
        Resources.getSystem().configuration.locales[0]?.language ?: SupportedLanguages.DEFAULT_CODE
}
