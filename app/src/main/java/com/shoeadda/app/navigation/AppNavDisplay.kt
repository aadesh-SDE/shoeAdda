package com.shoeadda.app.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay

/**
 * App shell: the Material 3 bottom bar and the Navigation 3 display.
 * The five tab screens are placeholders until their features are built.
 */
@Composable
fun AppNavDisplay() {
    val backStack = rememberNavBackStack(HomeKey)
    val currentKey: NavKey? = backStack.lastOrNull()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                bottomTabs.forEach { tab ->
                    val label = stringResource(tab.label)
                    NavigationBarItem(
                        selected = currentKey == tab.key,
                        onClick = {
                            if (currentKey != tab.key) {
                                backStack.clear()
                                backStack.add(tab.key)
                            }
                        },
                        icon = { Icon(imageVector = tab.icon, contentDescription = null) },
                        label = { Text(label) },
                    )
                }
            }
        },
    ) { innerPadding ->
        NavDisplay(
            backStack = backStack,
            modifier = Modifier.padding(innerPadding),
            onBack = { if (backStack.size > 1) backStack.removeLastOrNull() },
            entryProvider = entryProvider {
                entry<HomeKey> { PlaceholderScreen(stringResource(com.shoeadda.app.R.string.tab_home)) }
                entry<CategoriesKey> { PlaceholderScreen(stringResource(com.shoeadda.app.R.string.tab_categories)) }
                entry<WishlistKey> { PlaceholderScreen(stringResource(com.shoeadda.app.R.string.tab_wishlist)) }
                entry<CartKey> { PlaceholderScreen(stringResource(com.shoeadda.app.R.string.tab_cart)) }
                entry<ProfileKey> { PlaceholderScreen(stringResource(com.shoeadda.app.R.string.tab_profile)) }
            },
        )
    }
}

@Composable
private fun PlaceholderScreen(title: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = title, style = MaterialTheme.typography.titleLarge)
    }
}
