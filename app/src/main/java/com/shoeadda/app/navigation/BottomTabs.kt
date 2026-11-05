package com.shoeadda.app.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import com.shoeadda.app.R

/** One entry in the bottom navigation bar. */
data class BottomTab(
    val key: NavKey,
    @StringRes val label: Int,
    val icon: ImageVector,
)

/** Order matches the design: Home, Categories, Wishlist, Cart, Profile. */
val bottomTabs: List<BottomTab> = listOf(
    BottomTab(HomeKey, R.string.tab_home, Icons.Outlined.Home),
    BottomTab(CategoriesKey, R.string.tab_categories, Icons.Outlined.GridView),
    BottomTab(WishlistKey, R.string.tab_wishlist, Icons.Outlined.FavoriteBorder),
    BottomTab(CartKey, R.string.tab_cart, Icons.Outlined.ShoppingCart),
    BottomTab(ProfileKey, R.string.tab_profile, Icons.Outlined.Person),
)
