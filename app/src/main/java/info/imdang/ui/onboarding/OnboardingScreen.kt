package info.imdang.ui.onboarding

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import info.imdang.component.common.image.Icon
import info.imdang.component.common.modifier.isVisible
import info.imdang.component.system.button.CommonButton
import info.imdang.component.theme.Gray100
import info.imdang.component.theme.Gray25
import info.imdang.component.theme.Gray50
import info.imdang.component.theme.Gray900
import info.imdang.component.theme.ImdangTheme
import info.imdang.component.theme.Orange500
import info.imdang.component.theme.T500_16_22_4
import info.imdang.component.theme.T700_24_33_6
import info.imdang.resource.R
import info.imdang.ui.join.JOIN_SCREEN
import kotlinx.coroutines.launch

const val ONBOARDING_SCREEN = "onboarding"

fun NavGraphBuilder.onboardingScreen(navController: NavController) {
    composable(route = ONBOARDING_SCREEN) {
        OnboardingScreen(navController = navController)
    }
}

@Composable
private fun OnboardingScreen(navController: NavController) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { contentPadding ->
            OnboardingContent(
                navController = navController,
                contentPadding = contentPadding
            )
        }
    )
}

@Composable
private fun OnboardingContent(
    navController: NavController,
    contentPadding: PaddingValues
) {
    val pagerState = rememberPagerState { 3 }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .padding(bottom = contentPadding.calculateBottomPadding())
            .fillMaxSize()
            .background(color = Gray50)
            .padding(top = contentPadding.calculateTopPadding())
    ) {
        HorizontalPager(state = pagerState) { index ->
            when (index) {
                0 -> OnboardingPage(
                    image = R.drawable.img_onboarding_1,
                    title = stringResource(id = R.string.onboarding_title1),
                    content = stringResource(id = R.string.onboarding_description1)
                )

                1 -> OnboardingPage(
                    image = R.drawable.img_onboarding_2,
                    title = stringResource(id = R.string.onboarding_title2),
                    content = stringResource(id = R.string.onboarding_description2)
                )

                2 -> OnboardingPage(
                    image = R.drawable.img_onboarding_3,
                    title = stringResource(id = R.string.onboarding_title3),
                    content = stringResource(id = R.string.onboarding_description3)
                )
            }
        }
        Icon(
            modifier = Modifier
                .isVisible(pagerState.currentPage == 0)
                .padding(all = 12.dp)
                .size(40.dp)
                .clip(CircleShape)
                .clickable {
                    navController.popBackStack()
                }
                .padding(8.dp),
            iconResource = R.drawable.ic_back,
            tint = Gray900
        )
        LazyRow(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 224.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items(3) { index ->
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(
                            if (index == pagerState.currentPage) Orange500 else Gray100
                        )
                )
            }
        }
        CommonButton(
            modifier = Modifier.align(Alignment.BottomCenter),
            buttonText = stringResource(R.string.next),
            onClick = {
                if (pagerState.currentPage < 2) {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(page = pagerState.currentPage + 1)
                    }
                } else {
                    navController.navigate(route = JOIN_SCREEN)
                }
            }
        )
    }
}

@Composable
private fun OnboardingPage(
    @DrawableRes image: Int,
    title: String,
    content: String
) {
    Column(modifier = Modifier.background(color = Gray25)) {
        Image(
            modifier = Modifier
                .padding(bottom = 52.dp)
                .fillMaxWidth()
                .weight(1f)
                .background(Gray50)
                .padding(top = 62.dp),
            painter = painterResource(id = image),
            contentDescription = null
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 128.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = T700_24_33_6,
                color = Gray900,
                textAlign = TextAlign.Center
            )
            Text(
                text = content,
                style = T500_16_22_4,
                color = Gray900,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
private fun OnboardingScreenPreview() {
    ImdangTheme {
        OnboardingScreen(navController = rememberNavController())
    }
}
