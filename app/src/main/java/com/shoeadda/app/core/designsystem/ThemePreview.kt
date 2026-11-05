package com.shoeadda.app.core.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/** Android Studio preview of the colors, type scale and shapes in light and dark. Not shown in the app. */
@Composable
private fun ThemeSamples() {
    Surface(color = MaterialTheme.colorScheme.background) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text("WALK YOUR WAY", style = MaterialTheme.typography.displayLarge)
            Text("PAGE TITLE", style = MaterialTheme.typography.titleLarge)
            Text("Body text in Archivo, 15sp.", style = MaterialTheme.typography.bodyLarge)
            Text("Caption, 12sp", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text("₹12,499", style = ShoeAddaTheme.priceStyle)
            Text("₹12,999 4% off", style = MaterialTheme.typography.bodyMedium, color = ShoeAddaTheme.extraColors.sale)
            Button(onClick = {}) { Text("Add to cart") }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Swatch(MaterialTheme.colorScheme.primary)
                Swatch(MaterialTheme.colorScheme.surfaceContainer)
                Swatch(MaterialTheme.colorScheme.outline)
                Swatch(ShoeAddaTheme.extraColors.photoBackdrop)
                Swatch(ShoeAddaTheme.extraColors.sale)
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(MaterialTheme.colorScheme.surfaceContainer, MaterialTheme.shapes.medium),
            )
        }
    }
}

@Composable
private fun Swatch(color: Color) {
    Box(modifier = Modifier.size(40.dp).background(color, RoundedCornerShape(8.dp)))
}

@Preview(name = "Light", showBackground = true)
@Composable
private fun ThemePreviewLight() = ShoeAddaTheme(darkTheme = false) { ThemeSamples() }

@Preview(name = "Dark", showBackground = true)
@Composable
private fun ThemePreviewDark() = ShoeAddaTheme(darkTheme = true) { ThemeSamples() }
