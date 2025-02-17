package info.imdang.component.common.image

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import coil.compose.rememberAsyncImagePainter

@Composable
fun Icon(
    @DrawableRes iconResource: Int,
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    androidx.compose.material3.Icon(
        modifier = modifier,
        painter = rememberAsyncImagePainter(
            model = iconResource,
            placeholder = painterResource(id = iconResource)
        ),
        tint = tint,
        contentDescription = null
    )
}
