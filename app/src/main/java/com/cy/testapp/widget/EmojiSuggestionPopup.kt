package com.cy.testapp.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.coerceIn
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import com.chenyue404.androidlib.logcat.L
import kotlinx.coroutines.launch

@Composable
fun EmojiSuggestionPopup(
    textFieldValue: String,
    textFieldPosition: Rect?,
    onDismissRequest: () -> Unit = {},
    visible: Boolean = true
) {
    val density = LocalDensity.current
    val coroutineScope = rememberCoroutineScope()

    val emojis = remember(textFieldValue) {
        if (isSingleEmoji(textFieldValue)) {
//            generateRandomEmojis(Random.nextInt(40, 101))
            generateRandomEmojis(30)
        } else emptyList()
    }

    var currentHeight by remember(textFieldValue) { mutableStateOf(80.dp) }

    LaunchedEffect(emojis.size) {
        if (emojis.isNotEmpty() && currentHeight < 75.dp) {
            currentHeight = 80.dp
        }
    }

    val popupWidthDp = remember(textFieldPosition) {
        textFieldPosition?.let { with(density) { it.width.toDp() } } ?: 0.dp
    }

    val maxContentHeight = remember(emojis.size, popupWidthDp) {
        if (emojis.isEmpty() || popupWidthDp <= 0.dp) return@remember 0.dp

        val gridPadding = 12.dp
        val itemSpacing = 8.dp

        val availableWidth = popupWidthDp - gridPadding * 2 - itemSpacing * 4
        val itemSize = (availableWidth / 5f).coerceAtLeast(56.dp)

        val rowCount = (emojis.size + 4) / 5
        val totalHeight = (rowCount * itemSize) +
                ((rowCount - 1).coerceAtLeast(0) * itemSpacing) +
                (gridPadding * 2) + 20.dp   // 安全边距

//        totalHeight.coerceAtMost(540.dp)
        totalHeight
    }

    val displayHeight = currentHeight.coerceIn(0.dp, maxContentHeight)

    if (!visible || emojis.isEmpty() || textFieldPosition == null || displayHeight <= 0.dp) {
        return
    }

    // 获取键盘高度
//    val imeBottom = WindowInsets.ime.getBottom(density)

    val popupPositionProvider = remember(
        textFieldPosition, maxContentHeight,
//        imeBottom,
        density
    ) {
        object : PopupPositionProvider {
            override fun calculatePosition(
                anchorBounds: IntRect,
                windowSize: IntSize,
                layoutDirection: LayoutDirection,
                popupContentSize: IntSize
            ): IntOffset {
                var anchorTopY = textFieldPosition.top
                L.d { "anchorTopY: $anchorTopY, displayHeight: $displayHeight" }
                L.d { "anchorBounds: $anchorBounds, windowSize: $windowSize, layoutDirection: $layoutDirection, popupContentSize: $popupContentSize" }

                // 轻微补偿键盘（避免盖住或间隙过大），系数很小
//                if (imeBottom > 0) {
//                    anchorTopY -= (imeBottom * 0.25f)   // 0.25 是关键系数，可微调
//                }

                val maxHeightPx = with(density) { maxContentHeight.toPx() }
//                val popupY = (anchorTopY - maxHeightPx).toInt()
                val displayHeightPx = with(density) { displayHeight.toPx() }
                val popupY = (anchorTopY - popupContentSize.height).toInt()

                return IntOffset(
                    x = textFieldPosition.left.toInt(),
                    y = popupY
                )
            }
        }
    }
    val draggableState = rememberDraggableState { delta ->
        val deltaDp = with(density) { delta.toDp() }
        currentHeight = (currentHeight - deltaDp).coerceIn(0.dp, maxContentHeight)
    }
    Popup(
        popupPositionProvider = popupPositionProvider,
        onDismissRequest = onDismissRequest,
        properties = PopupProperties(
            focusable = false,
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            clippingEnabled = false
        )
    ) {
        Box(
            modifier = Modifier
                .width(popupWidthDp)
                .height(maxContentHeight)
        ) {
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .width(popupWidthDp)
                    .height(displayHeight)
//                    .pointerInput(Unit) {
//                        detectVerticalDragGestures { change, dragAmount ->
//                            change.consume()
//                            val dragDp = with(density) { dragAmount.toDp() }
//                            currentHeight =
//                                (currentHeight - dragDp).coerceIn(0.dp, maxContentHeight)
//                        }
//                    }
                    .draggable(
                        state = draggableState,
                        orientation = Orientation.Vertical,
                        // 关键：支持 fling（松手惯性）
                        onDragStopped = { velocity ->
                            coroutineScope.launch {
                                // 根据松手速度进行惯性动画
                                val velocityDpPerSecond =
                                    with(density) { velocity.toDp() * 1000f / 60f } // 粗略转换
                                // 这里可以进一步用 AnimationSpec 做更自然的衰减

                                // 简单实现：根据速度方向继续移动一段距离
                                val targetHeight = if (velocity < 0) {
                                    maxContentHeight   // 向上甩 → 展开到最大
                                } else {
                                    80.dp              // 向下甩 → 收起到一行
                                }

                                currentHeight = targetHeight
                            }
                        }
                    ),
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.98f),
                tonalElevation = 8.dp,
                shadowElevation = 12.dp
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(5),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    userScrollEnabled = false
                ) {
                    itemsIndexed(emojis) { index, emoji ->
                        Box(
                            modifier = Modifier
                                .aspectRatio(1f)
                                .background(
                                    MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f),
                                    RoundedCornerShape(12.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = emoji + index,
                                style = MaterialTheme.typography.headlineMedium
                            )
                        }
                    }
                }
            }
        }
    }
}

// ==================== 辅助函数 ====================
private fun isSingleEmoji(text: String): Boolean {
    if (text.isEmpty()) return false
    // 简单判断是否只有一个 emoji（可根据需要用更精确的 unicode range）
    return text.codePointCount(0, text.length) == 1 &&
            Character.isSupplementaryCodePoint(text.codePointAt(0))
}

private fun generateRandomEmojis(count: Int): List<String> {
    val emojiPool = listOf(
        "😀", "😂", "🥰", "😍", "😎", "🤩", "🥳", "😢", "😭", "😡",
        "👍", "❤️", "🔥", "✨", "🎉", "🚀", "🌈", "🍎", "🍕", "🐶",
        "🐱", "🦄", "🌟", "💡", "🎸", "⚽", "🏀", "🎂", "☕", "🍦"
        // 可继续扩展更多 emoji
    )
    return List(count) { emojiPool.random() }
}