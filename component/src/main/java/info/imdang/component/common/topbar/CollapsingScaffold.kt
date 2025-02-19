package info.imdang.component.common.topbar

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDecay
import androidx.compose.animation.core.animateTo
import androidx.compose.animation.core.spring
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.MutableWindowInsets
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.onConsumedWindowInsetsChanged
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.TopAppBarState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import info.imdang.component.theme.Gray25
import kotlin.math.abs

/**
 * Collapsing이 필요한 화면에서 Scaffold를 대신해서 사용
 * 기존의 TopAppBar는 minHeight가 56으로 고정되어 있어 대체 구현
 *
 * @param scrollBehavior CollapsingScaffold 파일의 exitUntilCollapsedScrollBehavior() 함수를 사용
 * @param topBar 스크롤과 상관없이 고정되어 있는 TopBar 영역
 * @param collapsingContent content의 스크롤에 따라 Collapsing, Expand 되는 영역
 * @param bottomBar 스크롤과 상관없이 고정되어 있는 BottomBar 영역
 * @param content 스크롤이 되는 Composable 영역, 보통의 경우 LazyColumn을 사용
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CollapsingScaffold(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
    containerColor: Color = Gray25,
    onCollapseChanged: (isCollapsed: Boolean) -> Unit = {},
    topBar: @Composable () -> Unit = {},
    collapsingContent: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    val localDensity = LocalDensity.current
    var topBarHeight by remember { mutableStateOf(0.dp) }
    var collapsingContentHeight by remember { mutableFloatStateOf(0f) }
    val heightOffsetLimit = -collapsingContentHeight
    val contentWindowInsets = ScaffoldDefaults.contentWindowInsets
    val safeInsets = remember(contentWindowInsets) { MutableWindowInsets(contentWindowInsets) }
    val height = with(localDensity) {
        (collapsingContentHeight + scrollBehavior.state.heightOffset) / density
    }.dp
    val offset = with(localDensity) { scrollBehavior.state.heightOffset / density }.dp
    val appBarDragModifier = if (scrollBehavior.isPinned) {
        Modifier
    } else {
        Modifier.draggable(
            orientation = Orientation.Vertical,
            state = rememberDraggableState { delta ->
                scrollBehavior.state.heightOffset += delta
            },
            onDragStopped = { velocity ->
                settleAppBar(
                    scrollBehavior.state,
                    velocity,
                    scrollBehavior.flingAnimationSpec,
                    scrollBehavior.snapAnimationSpec
                )
            }
        )
    }

    SideEffect {
        if (scrollBehavior.state.heightOffsetLimit != heightOffsetLimit) {
            scrollBehavior.state.heightOffsetLimit = heightOffsetLimit
        }
    }

    Box(
        modifier = modifier
            .onConsumedWindowInsetsChanged { consumedWindowInsets ->
                safeInsets.insets = contentWindowInsets.exclude(consumedWindowInsets)
            }
            .background(containerColor)
            .padding(bottom = safeInsets.asPaddingValues().calculateBottomPadding())
    ) {
        Column {
            Box(
                modifier = Modifier
                    .background(containerColor)
                    .onGloballyPositioned { coordinates ->
                        topBarHeight = with(localDensity) { coordinates.size.height.toDp() }
                    }
            ) {
                Column {
                    Box(
                        modifier = Modifier.height(
                            safeInsets.asPaddingValues().calculateTopPadding()
                        )
                    )
                    topBar()
                }
            }

            Surface(
                modifier = Modifier
                    .onGloballyPositioned { coordinates ->
                        collapsingContentHeight = coordinates.size.height.toFloat()
                    }
                    .then(appBarDragModifier)
                    .zIndex(-1f)
                    .fillMaxWidth()
                    .graphicsLayer {
                        translationY = offset.toPx()
                        onCollapseChanged(collapsingContentHeight + translationY <= 0f)
                    },
                color = containerColor,
                contentColor = containerColor
            ) {
                collapsingContent()
            }
        }

        Column {
            Spacer(modifier = Modifier.height(height + topBarHeight))

            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = 9.dp)
            ) {
                content()
            }

            bottomBar()
        }

        Box(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd)
        ) {
            floatingActionButton()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
private suspend fun settleAppBar(
    state: TopAppBarState,
    velocity: Float,
    flingAnimationSpec: DecayAnimationSpec<Float>?,
    snapAnimationSpec: AnimationSpec<Float>?
): Velocity {
    if (state.collapsedFraction < 0.01f || state.collapsedFraction == 1f) {
        return Velocity.Zero
    }
    var remainingVelocity = velocity
    if (flingAnimationSpec != null && abs(velocity) > 1f) {
        var lastValue = 0f
        AnimationState(
            initialValue = 0f,
            initialVelocity = velocity
        )
            .animateDecay(flingAnimationSpec) {
                val delta = value - lastValue
                val initialHeightOffset = state.heightOffset
                state.heightOffset = initialHeightOffset + delta
                val consumed = abs(initialHeightOffset - state.heightOffset)
                lastValue = value
                remainingVelocity = this.velocity

                if (abs(delta - consumed) > 0.9f) this.cancelAnimation()
            }
    }

    if (snapAnimationSpec != null) {
        if (state.heightOffset < 0 &&
            state.heightOffset > state.heightOffsetLimit
        ) {
            AnimationState(initialValue = state.heightOffset).animateTo(
                if (state.collapsedFraction < 0.1f) {
                    0f
                } else {
                    state.heightOffsetLimit
                },
                animationSpec = snapAnimationSpec
            ) { state.heightOffset = value }
        }
    }

    return Velocity(0f, remainingVelocity)
}

@ExperimentalMaterial3Api
@Composable
fun exitUntilCollapsedScrollBehavior(
    state: TopAppBarState = rememberTopAppBarState(),
    canScroll: () -> Boolean = { true },
    snapAnimationSpec: AnimationSpec<Float>? = spring(stiffness = Spring.StiffnessMediumLow),
    flingAnimationSpec: DecayAnimationSpec<Float>? = rememberSplineBasedDecay(),
    isEnableFling: Boolean = false
): TopAppBarScrollBehavior =
    ExitUntilCollapsedScrollBehavior(
        state = state,
        snapAnimationSpec = snapAnimationSpec,
        flingAnimationSpec = flingAnimationSpec,
        canScroll = canScroll,
        isEnableFling = isEnableFling
    )

@OptIn(ExperimentalMaterial3Api::class)
private class ExitUntilCollapsedScrollBehavior(
    override val state: TopAppBarState,
    override val snapAnimationSpec: AnimationSpec<Float>?,
    override val flingAnimationSpec: DecayAnimationSpec<Float>?,
    val canScroll: () -> Boolean = { true },
    isEnableFling: Boolean
) : TopAppBarScrollBehavior {
    override val isPinned: Boolean = false
    override var nestedScrollConnection =
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if (!canScroll() || available.y > 0f) return Offset.Zero

                val prevHeightOffset = state.heightOffset
                state.heightOffset += available.y
                return if (prevHeightOffset != state.heightOffset) {
                    available.copy(x = 0f)
                } else {
                    Offset.Zero
                }
            }

            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                if (!canScroll()) return Offset.Zero
                state.contentOffset += consumed.y

                if (available.y < 0f || consumed.y < 0f) {
                    val oldHeightOffset = state.heightOffset
                    state.heightOffset += consumed.y
                    return Offset(0f, state.heightOffset - oldHeightOffset)
                }

                if (consumed.y == 0f && available.y > 0) {
                    state.contentOffset = 0f
                }

                if (available.y > 0f) {
                    val oldHeightOffset = state.heightOffset
                    state.heightOffset += available.y
                    return Offset(0f, state.heightOffset - oldHeightOffset)
                }
                return Offset.Zero
            }

            override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
                val superConsumed = super.onPostFling(consumed, available)
                return if (isEnableFling) {
                    superConsumed + settleAppBar(
                        state,
                        available.y,
                        flingAnimationSpec,
                        snapAnimationSpec
                    )
                } else {
                    superConsumed
                }
            }
        }
}
