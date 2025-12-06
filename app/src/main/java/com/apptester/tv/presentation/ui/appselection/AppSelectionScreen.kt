@file:OptIn(ExperimentalTvMaterial3Api::class)

package com.apptester.tv.presentation.ui.appselection

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusRestorer
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.tv.material3.CardDefaults
import androidx.tv.material3.CompactCard
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import com.apptester.tv.R
import com.apptester.tv.data.entity.FirebaseApp
import com.apptester.tv.presentation.PositionFocusedItemInLazyLayout
import com.apptester.tv.presentation.ifElse
import com.apptester.tv.theme.AppTheme
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalComposeUiApi::class, ExperimentalTvMaterial3Api::class)
@Composable
fun AppSelectionScreen(
    modifier: Modifier = Modifier,
    viewModel: AppSelectionViewModel = koinViewModel<AppSelectionViewModel>(),
) {
    val state = viewModel.state.collectAsState()

    when (state.value) {
        is AppSelectionState.Success -> {
            AppSelectionContent(
                apps = (state.value as AppSelectionState.Success).apps
            )
        }
        is AppSelectionState.Error -> {
            Text("Error!! ${(state.value as AppSelectionState.Error).message}")
            // TODO("Display a Screen Error with retry option")
        }

        AppSelectionState.Loading -> {
            CircularProgressIndicator()
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AppSelectionContent(
    modifier: Modifier = Modifier,
    apps: List<FirebaseApp>,
) {

    var selectedCard by remember { mutableStateOf(apps.first()) }

    Box(modifier = modifier.fillMaxSize()) {
        // background image
        Image(
            painter = painterResource(id = R.mipmap.ic_launcher), // TODO: Replace to get from selectedCard.image
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        // gradient and text
        Box(
            modifier = Modifier
                .fillMaxSize()
                .immersiveListGradient(),
            contentAlignment = Alignment.TopStart
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 58.dp, bottom = 16.dp)
                    .width(480.dp)
                    .wrapContentHeight()
            ) {
                Text(
                    text = selectedCard.displayName,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.6f)
                )

                Text(
                    text = selectedCard.name,
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = selectedCard.packageName,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.8f)
                )
            }
        }

        val density = LocalDensity.current
        var fullWidth by remember { mutableFloatStateOf(1f) }
        val firstChildFr = remember { FocusRequester() }

        PositionFocusedItemInLazyLayout(
            parentFraction = 58f / fullWidth,
            childFraction = 0f
        ) {
            LazyRow(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
                    .focusRestorer(firstChildFr)
                    .onPlaced {
                        with(density) {
                            fullWidth = it.size.width.toDp().value
                        }
                    },
                contentPadding = PaddingValues(start = 58.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                itemsIndexed(apps) { index, card ->
                    CompactCard(
                        modifier = Modifier
                            .width(140.dp)
                            .aspectRatio(1f)
                            .ifElse(index == 0, Modifier.focusRequester(firstChildFr))
                            .onFocusChanged {
                                if (it.isFocused) {
                                    selectedCard = card
                                }
                            },
                        onClick = {},
                        image = {
                            Image(
                                painter = painterResource(id = R.mipmap.ic_launcher),  // TODO: Replace to get from selectedCard.image
                                contentDescription = "Image",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.FillBounds
                            )
                        },
                        title = {},
                        colors = CardDefaults.colors(containerColor = Color.Transparent)
                    )
                }
            }
        }
    }

}

@SuppressLint("SuspiciousModifierThen", "UnnecessaryComposedModifier")
private fun Modifier.immersiveListGradient(): Modifier = composed {
    val color = MaterialTheme.colorScheme.surface

    val colorAlphaList = listOf(1.0f, 0.2f, 0.0f)
    val colorStopList = listOf(0.2f, 0.8f, 0.9f)

    val colorAlphaList2 = listOf(1.0f, 0.1f, 0.0f)
    val colorStopList2 = listOf(0.1f, 0.4f, 0.9f)
    this
        .then(
            background(
                brush = Brush.linearGradient(
                    colorStopList[0] to color.copy(alpha = colorAlphaList[0]),
                    colorStopList[1] to color.copy(alpha = colorAlphaList[1]),
                    colorStopList[2] to color.copy(alpha = colorAlphaList[2]),
                    start = Offset(0.0f, 0.0f),
                    end = Offset(Float.POSITIVE_INFINITY, 0.0f)
                )
            )
        )
        .then(
            background(
                brush = Brush.linearGradient(
                    colorStopList2[0] to color.copy(alpha = colorAlphaList2[0]),
                    colorStopList2[1] to color.copy(alpha = colorAlphaList2[1]),
                    colorStopList2[2] to color.copy(alpha = colorAlphaList2[2]),
                    start = Offset(0f, Float.POSITIVE_INFINITY),
                    end = Offset(0f, 0f)
                )
            )
        )
}

@Preview(
    device = Devices.TV_720p,
    showBackground = true
)
@Composable
private fun AppSelectionScreenPreview() {
    AppTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            AppSelectionScreen()
        }

    }

}