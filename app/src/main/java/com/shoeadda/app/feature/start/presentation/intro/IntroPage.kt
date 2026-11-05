package com.shoeadda.app.feature.start.presentation.intro

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shoeadda.app.R

enum class IntroBackdrop { Photo, Warm }

enum class IntroChipStyle { Outlined, Primary, Dark }

/** A small label floating over the illustration. [alignment] and the offsets position it inside the 330 dp art area. */
data class IntroChipSpec(
    @StringRes val text: Int,
    val style: IntroChipStyle,
    val alignment: Alignment,
    val x: Dp,
    val y: Dp,
)

data class IntroPage(
    @StringRes val title: Int,
    @StringRes val body: Int,
    @DrawableRes val art: Int,
    @StringRes val artDescription: Int,
    val backdrop: IntroBackdrop,
    val chips: List<IntroChipSpec>,
    /** Illustration size and position inside the 330 dp art area. */
    val artWidth: Dp,
    val artOffsetY: Dp,
    val artRotation: Float = 0f,
)

val introPages: List<IntroPage> = listOf(
    IntroPage(
        title = R.string.intro_1_title,
        body = R.string.intro_1_body,
        art = R.drawable.ic_intro_sneaker,
        artDescription = R.string.cd_sneaker_illustration,
        backdrop = IntroBackdrop.Photo,
        chips = listOf(
            IntroChipSpec(R.string.chip_uk_size, IntroChipStyle.Outlined, Alignment.TopStart, 6.dp, 46.dp),
            IntroChipSpec(R.string.chip_true_to_size, IntroChipStyle.Primary, Alignment.TopEnd, (-2).dp, 150.dp),
            IntroChipSpec(R.string.chip_foot_length, IntroChipStyle.Outlined, Alignment.BottomStart, 14.dp, (-50).dp),
        ),
        artWidth = 300.dp,
        artOffsetY = 78.dp,
        artRotation = -6f,
    ),
    IntroPage(
        title = R.string.intro_2_title,
        body = R.string.intro_2_body,
        art = R.drawable.ic_intro_box,
        artDescription = R.string.cd_box_illustration,
        backdrop = IntroBackdrop.Warm,
        chips = listOf(
            IntroChipSpec(R.string.chip_order_placed, IntroChipStyle.Outlined, Alignment.TopStart, 4.dp, 52.dp),
            IntroChipSpec(R.string.chip_on_the_way, IntroChipStyle.Dark, Alignment.TopEnd, 0.dp, 136.dp),
            IntroChipSpec(R.string.chip_free_exchange, IntroChipStyle.Outlined, Alignment.BottomStart, 10.dp, (-46).dp),
        ),
        artWidth = 210.dp,
        artOffsetY = 62.dp,
    ),
)
