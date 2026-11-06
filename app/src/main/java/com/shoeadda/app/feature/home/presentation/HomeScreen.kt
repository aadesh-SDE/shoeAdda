package com.shoeadda.app.feature.home.presentation

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shoeadda.app.R
import com.shoeadda.app.core.designsystem.BarlowCondensed
import com.shoeadda.app.core.designsystem.BrandCocoa
import com.shoeadda.app.core.designsystem.ShoeAddaTheme
import com.shoeadda.app.core.designsystem.asHeadline
import com.shoeadda.app.core.designsystem.components.ProductCard
import com.shoeadda.app.feature.catalog.domain.model.Shoe
import com.shoeadda.app.feature.home.domain.model.Brand
import com.shoeadda.app.feature.home.domain.model.HomeSections

// The buttons on this screen open other screens that are built later (search, product list, product detail,
// cart, notifications, wishlist). They are wired up in those features.

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    HomeContent(state = state)
}

@Composable
private fun HomeContent(state: HomeUiState) {
    Column(modifier = Modifier.fillMaxSize()) {
        HomeTopBar()
        HorizontalDivider(color = MaterialTheme.colorScheme.outline)
        when (state) {
            HomeUiState.Loading -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            is HomeUiState.Content -> HomeSectionsList(state.sections)
        }
    }
}

@Composable
private fun HomeTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .padding(start = 16.dp, end = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(BrandCocoa),
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_launcher_foreground),
                    contentDescription = stringResource(R.string.app_name),
                )
            }
            Text(
                text = buildAnnotatedString {
                    append("SHOE")
                    withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) { append("ADDA") }
                },
                style = TextStyle(
                    fontFamily = BarlowCondensed,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    letterSpacing = 0.015.em,
                ),
            )
        }
        Row {
            IconButton(onClick = { }) {
                Icon(Icons.Outlined.Notifications, contentDescription = stringResource(R.string.cd_notifications))
            }
            IconButton(onClick = { }) {
                Icon(Icons.Outlined.ShoppingCart, contentDescription = stringResource(R.string.cd_cart))
            }
        }
    }
}

@Composable
private fun HomeSectionsList(sections: HomeSections) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item { SearchField(modifier = Modifier.padding(horizontal = 16.dp)) }
        item { PromoBanner(modifier = Modifier.padding(horizontal = 16.dp)) }
        item { CategoryChips() }
        if (sections.brands.isNotEmpty()) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    SectionHeader(title = stringResource(R.string.section_brands))
                    BrandRow(sections.brands)
                }
            }
        }
        if (sections.newArrivals.isNotEmpty()) {
            item { SectionHeader(title = stringResource(R.string.section_new_arrivals)) }
            shoeGrid(sections.newArrivals)
        }
        if (sections.trending.isNotEmpty()) {
            item { SectionHeader(title = stringResource(R.string.section_trending)) }
            shoeGrid(sections.trending)
        }
    }
}

@Composable
private fun SearchField(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(46.dp)
            .clip(MaterialTheme.shapes.medium)
            .clickable { },
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceContainer,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Icon(Icons.Outlined.Search, contentDescription = null, modifier = Modifier.size(20.dp))
            Text(text = stringResource(R.string.home_search_hint), style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun PromoBanner(modifier: Modifier = Modifier) {
    val labelColor = Color(0xFFE8DFD0)
    val bodyColor = Color(0xFFF0E8DA)
    val white = Color(0xFFFBFBFB)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(158.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(BrandCocoa),
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = 34.dp, y = 40.dp)
                .size(190.dp)
                .background(ShoeAddaTheme.extraColors.photoBackdrop, CircleShape),
        )
        Image(
            painter = painterResource(R.drawable.ic_intro_sneaker),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = 8.dp, y = (-6).dp)
                .width(150.dp)
                .rotate(-8f),
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 18.dp)
                .width(190.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Text(
                text = stringResource(R.string.home_banner_label).uppercase(),
                style = MaterialTheme.typography.labelSmall,
                color = labelColor,
            )
            Text(
                text = stringResource(R.string.home_banner_title).asHeadline(),
                style = MaterialTheme.typography.displayLarge,
                color = white,
            )
            Text(
                text = stringResource(R.string.home_banner_subtitle),
                style = MaterialTheme.typography.bodySmall,
                color = bodyColor,
            )
        }
        Surface(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 20.dp, bottom = 16.dp)
                .clip(RoundedCornerShape(10.dp))
                .clickable { },
            shape = RoundedCornerShape(10.dp),
            color = white,
            contentColor = Color(0xFF131212),
        ) {
            Box(modifier = Modifier.height(36.dp).padding(horizontal = 16.dp), contentAlignment = Alignment.Center) {
                Text(text = stringResource(R.string.home_shop_now), style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}

private data class HomeCategory(@StringRes val label: Int)

private val homeCategories = listOf(
    HomeCategory(R.string.category_all),
    HomeCategory(R.string.category_men),
    HomeCategory(R.string.category_women),
    HomeCategory(R.string.category_sports),
    HomeCategory(R.string.category_casual),
)

@Composable
private fun CategoryChips() {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(homeCategories.size) { index ->
            val selected = index == 0
            FilterChip(
                selected = selected,
                onClick = { },
                label = { Text(stringResource(homeCategories[index].label), style = MaterialTheme.typography.labelLarge) },
                shape = CircleShape,
                border = null,
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    labelColor = MaterialTheme.colorScheme.onBackground,
                    selectedContainerColor = MaterialTheme.colorScheme.onBackground,
                    selectedLabelColor = MaterialTheme.colorScheme.background,
                ),
            )
        }
    }
}

@Composable
private fun SectionHeader(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(text = title.asHeadline(), style = MaterialTheme.typography.titleLarge)
        TextButton(onClick = { }) {
            Text(text = stringResource(R.string.action_see_all), style = MaterialTheme.typography.labelLarge)
        }
    }
}

@Composable
private fun BrandRow(brands: List<Brand>) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        items(brands, key = { it.name }) { brand ->
            Column(
                modifier = Modifier.width(64.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .background(MaterialTheme.colorScheme.surfaceContainer, CircleShape),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = initialsOf(brand.name),
                        style = TextStyle(fontFamily = BarlowCondensed, fontWeight = FontWeight.Bold, fontSize = 24.sp),
                    )
                }
                Text(
                    text = brand.name,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                )
            }
        }
    }
}

/** "Urban Sole" becomes "US", "Stride" becomes "S". Words such as "&" are skipped. */
private fun initialsOf(name: String): String {
    val letters = name.split(' ').mapNotNull { word -> word.firstOrNull { it.isLetterOrDigit() }?.uppercaseChar() }
    return letters.take(2).joinToString("")
}

/** Shoes in two columns. Each pair of shoes is one row of the list. */
private fun LazyListScope.shoeGrid(shoes: List<Shoe>) {
    items(shoes.chunked(2), key = { row -> row.first().id }) { row ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            row.forEach { shoe ->
                ProductCard(
                    shoe = shoe,
                    onClick = { },
                    onWishlistClick = { },
                    modifier = Modifier.weight(1f),
                )
            }
            if (row.size == 1) Spacer(modifier = Modifier.weight(1f))
        }
    }
}
