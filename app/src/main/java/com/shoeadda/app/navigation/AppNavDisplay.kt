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
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.shoeadda.app.R
import com.shoeadda.app.feature.start.presentation.StartDestination
import com.shoeadda.app.feature.start.presentation.intro.IntroScreen
import com.shoeadda.app.feature.start.presentation.language.LanguageScreen

private fun StartDestination.toKey(): NavKey = when (this) {
    StartDestination.Language -> LanguageKey
    StartDestination.Intro -> IntroKey
    StartDestination.Home -> HomeKey
}

/**
 * App shell: the Navigation 3 display and, on the five tab screens only, the Material 3 bottom bar.
 * The tab screens are placeholders until their features are built.
 */
@Composable
fun AppNavDisplay(startDestination: StartDestination) {
    val backStack = rememberNavBackStack(startDestination.toKey())
    val currentKey: NavKey? = backStack.lastOrNull()
    val showBottomBar = bottomTabs.any { it.key == currentKey }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    bottomTabs.forEach { tab ->
                        NavigationBarItem(
                            selected = currentKey == tab.key,
                            onClick = {
                                if (currentKey != tab.key) {
                                    backStack.clear()
                                    backStack.add(tab.key)
                                }
                            },
                            icon = { Icon(imageVector = tab.icon, contentDescription = null) },
                            label = { Text(stringResource(tab.label)) },
                        )
                    }
                }
            }
        },
    ) { innerPadding ->
        NavDisplay(
            backStack = backStack,
            modifier = if (showBottomBar) Modifier.padding(innerPadding) else Modifier,
            onBack = { if (backStack.size > 1) backStack.removeLastOrNull() },
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator(),
            ),
            entryProvider = entryProvider {
                entry<LanguageKey> {
                    LanguageScreen(onContinue = { backStack.add(IntroKey) })
                }
                entry<IntroKey> {
                    IntroScreen(
                        onFinished = {
                            backStack.clear()
                            backStack.add(HomeKey)
                        },
                    )
                }
                entry<HomeKey> { PlaceholderScreen(stringResource(R.string.tab_home)) }
                entry<CategoriesKey> { PlaceholderScreen(stringResource(R.string.tab_categories)) }
                entry<WishlistKey> { PlaceholderScreen(stringResource(R.string.tab_wishlist)) }
                entry<CartKey> { PlaceholderScreen(stringResource(R.string.tab_cart)) }
                entry<ProfileKey> { PlaceholderScreen(stringResource(R.string.tab_profile)) }
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
