package tachiyomi.presentation.core.components.material

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * M3 Navbar with no horizontal spacer
 * Modified to be a floating pill-style bar.
 *
 * @see [androidx.compose.material3.NavigationBar]
 */

@Composable
fun NavigationBar(
    modifier: Modifier = Modifier,
    containerColor: Color = NavigationBarDefaults.containerColor,
    contentColor: Color = MaterialTheme.colorScheme.contentColorFor(containerColor),
    tonalElevation: Dp = NavigationBarDefaults.Elevation,
    windowInsets: WindowInsets = NavigationBarDefaults.windowInsets,
    indicator: @Composable () -> Unit = {},
    content: @Composable RowScope.() -> Unit,
) {
    val roundedShape = RoundedCornerShape(24.dp)

    // Translucent glass without the cost of capturing and blurring the full screen.
    val frostedColor = containerColor.copy(alpha = 0.82f)
    val overLightScrim = Color.Black.copy(alpha = 0.08f)
    val glassBorder = Color.White.copy(alpha = 0.15f)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .windowInsetsPadding(windowInsets)
            .padding(bottom = 16.dp),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Surface(
            color = Color.Transparent, // We draw the background manually in modifiers
            contentColor = contentColor,
            tonalElevation = tonalElevation,
            shape = roundedShape,
            modifier = Modifier
                .widthIn(max = 360.dp)
                .fillMaxWidth()
                .shadow(elevation = 8.dp, shape = roundedShape)
                .then(modifier)
                .background(color = frostedColor, shape = roundedShape)
                .background(color = overLightScrim, shape = roundedShape)
                .border(width = 1.dp, color = glassBorder, shape = roundedShape),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
            ) {
                indicator()
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 4.dp)
                        .selectableGroup(),
                    content = content,
                )
            }
        }
    }
}
