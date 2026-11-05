package com.shoeadda.app.feature.start.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shoeadda.app.feature.start.presentation.intro.IntroChipStyle

/** A rounded label that floats over an intro illustration. It only decorates, so it is not clickable. */
@Composable
fun IntroChip(
    text: String,
    style: IntroChipStyle,
    modifier: Modifier = Modifier,
) {
    val colors = MaterialTheme.colorScheme
    val container = when (style) {
        IntroChipStyle.Outlined -> colors.background
        IntroChipStyle.Primary -> colors.primary
        IntroChipStyle.Dark -> colors.onBackground
    }
    val content = when (style) {
        IntroChipStyle.Outlined -> colors.onBackground
        IntroChipStyle.Primary -> colors.onPrimary
        IntroChipStyle.Dark -> colors.background
    }
    Surface(
        modifier = modifier,
        shape = CircleShape,
        color = container,
        contentColor = content,
        border = if (style == IntroChipStyle.Outlined) BorderStroke(1.dp, colors.outline) else null,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier
                .heightIn(min = 36.dp)
                .padding(horizontal = 14.dp, vertical = 8.dp),
            maxLines = 1,
        )
    }
}
