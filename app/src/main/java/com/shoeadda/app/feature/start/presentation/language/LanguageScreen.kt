package com.shoeadda.app.feature.start.presentation.language

import androidx.compose.foundation.border
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shoeadda.app.R
import com.shoeadda.app.core.designsystem.BrandCocoa
import com.shoeadda.app.core.designsystem.asHeadline
import com.shoeadda.app.core.designsystem.fontFamilyForLanguage
import com.shoeadda.app.feature.start.domain.model.AppLanguage

@Composable
fun LanguageScreen(
    onContinue: () -> Unit,
    viewModel: LanguageViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.events.collect { event ->
            when (event) {
                LanguageEvent.Continue -> onContinue()
            }
        }
    }

    LanguageContent(
        state = state,
        onLanguageSelected = viewModel::onLanguageSelected,
        onContinue = viewModel::onContinue,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LanguageContent(
    state: LanguageUiState,
    onLanguageSelected: (String) -> Unit,
    onContinue: () -> Unit,
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.language_title).asHeadline(),
                            style = MaterialTheme.typography.titleLarge,
                        )
                    },
                    actions = {
                        Box(
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .size(32.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(BrandCocoa),
                        ) {
                            Image(
                                painter = painterResource(R.drawable.ic_launcher_foreground),
                                contentDescription = stringResource(R.string.app_name),
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.outline)
            }
        },
        bottomBar = {
            Surface(color = MaterialTheme.colorScheme.background) {
                Column(modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)) {
                    HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                    Button(
                        onClick = onContinue,
                        enabled = state.selectedCode.isNotEmpty(),
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .height(52.dp),
                    ) {
                        Text(text = stringResource(R.string.action_continue), style = MaterialTheme.typography.labelLarge)
                    }
                }
            }
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            item {
                Column(
                    modifier = Modifier.padding(bottom = 6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Text(
                        text = stringResource(R.string.language_headline).asHeadline(),
                        style = MaterialTheme.typography.displayLarge,
                    )
                    Text(
                        text = stringResource(R.string.language_hint),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
            items(state.languages, key = { it.code }) { language ->
                LanguageRow(
                    language = language,
                    selected = language.code == state.selectedCode,
                    onClick = { onLanguageSelected(language.code) },
                )
            }
        }
    }
}

@Composable
private fun LanguageRow(
    language: AppLanguage,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val colors = MaterialTheme.colorScheme
    val shape = MaterialTheme.shapes.large
    val borderColor = if (selected) colors.primary else colors.outline

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .clip(shape)
            .border(BorderStroke(if (selected) 1.5.dp else 1.dp, borderColor), shape)
            .selectable(selected = selected, role = Role.RadioButton, onClick = onClick)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(1.dp)) {
            Text(
                text = language.nativeName,
                style = TextStyle(
                    fontFamily = fontFamilyForLanguage(language.code),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    lineHeight = 26.sp,
                ),
            )
            Text(
                text = language.englishName,
                style = MaterialTheme.typography.bodySmall,
                color = colors.onSurfaceVariant,
            )
        }
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(if (selected) colors.primary else colors.background)
                .border(2.dp, borderColor, CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            if (selected) {
                Icon(
                    imageVector = Icons.Outlined.Check,
                    contentDescription = null,
                    tint = colors.onPrimary,
                    modifier = Modifier.size(14.dp),
                )
            }
        }
    }
}
