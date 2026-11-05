package com.shoeadda.app.feature.start.presentation.intro

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.shoeadda.app.R
import com.shoeadda.app.core.designsystem.ShoeAddaTheme
import com.shoeadda.app.core.designsystem.asHeadline
import com.shoeadda.app.feature.start.presentation.components.IntroChip
import com.shoeadda.app.feature.start.presentation.components.PageDots
import kotlinx.coroutines.launch

@Composable
fun IntroScreen(
    onFinished: () -> Unit,
    viewModel: IntroViewModel = hiltViewModel(),
) {
    LaunchedEffect(viewModel) {
        viewModel.events.collect { event ->
            when (event) {
                IntroEvent.Finished -> onFinished()
            }
        }
    }

    IntroContent(onFinish = viewModel::onFinish)
}

@Composable
private fun IntroContent(onFinish: () -> Unit) {
    val pagerState = rememberPagerState(pageCount = { introPages.size })
    val scope = rememberCoroutineScope()
    val isLastPage = pagerState.currentPage == introPages.lastIndex

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .windowInsetsPadding(WindowInsets.systemBars),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextButton(onClick = onFinish, modifier = Modifier.heightIn(min = 48.dp)) {
                Text(text = stringResource(R.string.action_skip), style = MaterialTheme.typography.labelLarge)
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f),
        ) { index ->
            IntroPageContent(page = introPages[index])
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            PageDots(pageCount = introPages.size, currentPage = pagerState.currentPage)
            Button(
                onClick = {
                    if (isLastPage) {
                        onFinish()
                    } else {
                        scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                    }
                },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
            ) {
                Text(
                    text = stringResource(if (isLastPage) R.string.action_get_started else R.string.action_next),
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }
    }
}

@Composable
private fun IntroPageContent(page: IntroPage) {
    val extra = ShoeAddaTheme.extraColors
    val backdrop = when (page.backdrop) {
        IntroBackdrop.Photo -> extra.photoBackdrop
        IntroBackdrop.Warm -> extra.warmBackdrop
    }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        Box(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 12.dp)
                .fillMaxWidth()
                .height(330.dp),
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .size(300.dp)
                    .background(backdrop, CircleShape),
            )
            Image(
                painter = painterResource(page.art),
                contentDescription = stringResource(page.artDescription),
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = page.artOffsetY)
                    .width(page.artWidth)
                    .rotate(page.artRotation),
            )
            page.chips.forEach { chip ->
                IntroChip(
                    text = stringResource(chip.text),
                    style = chip.style,
                    modifier = Modifier
                        .align(chip.alignment)
                        .offset(x = chip.x, y = chip.y),
                )
            }
        }

        Column(
            modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                text = stringResource(page.title).asHeadline(),
                style = MaterialTheme.typography.displayLarge,
            )
            Text(
                text = stringResource(page.body),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
