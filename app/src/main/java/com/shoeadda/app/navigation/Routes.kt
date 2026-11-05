package com.shoeadda.app.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

// One key per screen. Navigation 3 keeps the back stack as a plain list of these keys.
// Keys for other screens are added with their feature.

@Serializable
data object LanguageKey : NavKey

@Serializable
data object IntroKey : NavKey

// Bottom tabs

@Serializable
data object HomeKey : NavKey

@Serializable
data object CategoriesKey : NavKey

@Serializable
data object WishlistKey : NavKey

@Serializable
data object CartKey : NavKey

@Serializable
data object ProfileKey : NavKey
