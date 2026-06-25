package eu.kanade.presentation.reader.appbars

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.SkipNext
import androidx.compose.material.icons.outlined.SkipPrevious
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import eu.kanade.presentation.reader.components.ChapterNavigatorType
import eu.kanade.tachiyomi.R
import eu.kanade.tachiyomi.ui.reader.setting.ReaderOrientation
import eu.kanade.tachiyomi.ui.reader.setting.ReadingMode
import tachiyomi.i18n.MR
import tachiyomi.presentation.core.i18n.stringResource

@Composable
fun ReaderBottomBar(
    chapterNavigatorType: ChapterNavigatorType,
    onNextChapter: () -> Unit,
    enabledNext: Boolean,
    onPreviousChapter: () -> Unit,
    enabledPrevious: Boolean,
    readingMode: ReadingMode,
    onClickReadingMode: () -> Unit,
    orientation: ReaderOrientation,
    onClickOrientation: () -> Unit,
    cropEnabled: Boolean,
    onClickCropBorder: () -> Unit,
    onClickSettings: () -> Unit,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
) {
    val isRtl = chapterNavigatorType == ChapterNavigatorType.HORIZONTAL_RTL
    val buttonColor = IconButtonDefaults.filledIconButtonColors(
        containerColor = Color.Transparent,
        disabledContainerColor = Color.Transparent,
    )

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Left Pill: Previous Chapter (or Next if RTL)
        Surface(
            shape = CircleShape,
            color = backgroundColor,
        ) {
            IconButton(
                enabled = if (isRtl) enabledNext else enabledPrevious,
                onClick = if (isRtl) onNextChapter else onPreviousChapter,
                colors = buttonColor,
            ) {
                Icon(
                    imageVector = Icons.Outlined.SkipPrevious,
                    contentDescription = stringResource(
                        if (isRtl) MR.strings.action_next_chapter else MR.strings.action_previous_chapter,
                    ),
                )
            }
        }

        // Center Capsule: Main Settings
        Surface(
            shape = CircleShape,
            color = backgroundColor,
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = onClickReadingMode) {
                    Icon(
                        painter = painterResource(readingMode.iconRes),
                        contentDescription = stringResource(MR.strings.viewer),
                    )
                }

                IconButton(onClick = onClickOrientation) {
                    Icon(
                        imageVector = orientation.icon,
                        contentDescription = stringResource(MR.strings.rotation_type),
                    )
                }

                IconButton(onClick = onClickCropBorder) {
                    Icon(
                        painter = painterResource(
                            if (cropEnabled) R.drawable.ic_crop_24dp else R.drawable.ic_crop_off_24dp,
                        ),
                        contentDescription = stringResource(MR.strings.pref_crop_borders),
                    )
                }

                IconButton(onClick = onClickSettings) {
                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = stringResource(MR.strings.action_settings),
                    )
                }
            }
        }

        // Right Pill: Next Chapter (or Previous if RTL)
        Surface(
            shape = CircleShape,
            color = backgroundColor,
        ) {
            IconButton(
                enabled = if (isRtl) enabledPrevious else enabledNext,
                onClick = if (isRtl) onPreviousChapter else onNextChapter,
                colors = buttonColor,
            ) {
                Icon(
                    imageVector = Icons.Outlined.SkipNext,
                    contentDescription = stringResource(
                        if (isRtl) MR.strings.action_previous_chapter else MR.strings.action_next_chapter,
                    ),
                )
            }
        }
    }
}
