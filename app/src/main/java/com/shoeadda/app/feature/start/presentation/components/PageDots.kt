package com.shoeadda.app.feature.start.presentation.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.shoeadda.app.R

/** Page indicator: the current page is a longer pill. Announced as "Page 1 of 2" for screen readers. */
@Composable
fun PageDots(
    pageCount: Int,
    currentPage: Int,
    modifier: Modifier = Modifier,
) {
    val description = stringResource(R.string.cd_page_of, currentPage + 1, pageCount)
    Row(
        modifier = modifier.semantics { contentDescription = description },
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        repeat(pageCount) { index ->
            val width by animateDpAsState(if (index == currentPage) 24.dp else 6.dp, label = "dotWidth")
            Box(
                modifier = Modifier
                    .width(width)
                    .height(6.dp)
                    .background(
                        color = if (index == currentPage) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                        shape = CircleShape,
                    ),
            )
        }
    }
}
