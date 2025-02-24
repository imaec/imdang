package info.imdang.app.ui.insight.write.basic

import android.Manifest
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import info.imdang.app.common.util.getFileFromContentUri
import info.imdang.app.common.util.launch
import info.imdang.app.common.util.rememberPermissionGranted
import info.imdang.app.common.util.rememberPermissionLauncher
import info.imdang.app.common.util.rememberPickMediaLauncher
import info.imdang.app.common.util.rememberTakePictureLauncher
import info.imdang.app.ui.insight.write.WriteInsightInit
import info.imdang.app.ui.insight.write.WriteInsightViewModel
import info.imdang.app.ui.insight.write.common.WriteInsightDetailContentView
import info.imdang.app.ui.insight.write.common.WriteInsightSelectionButtons
import info.imdang.app.ui.insight.write.common.WriteInsightTitle
import info.imdang.component.common.image.Icon
import info.imdang.component.common.modifier.visible
import info.imdang.component.system.textfield.CommonTextField
import info.imdang.component.theme.Gray100
import info.imdang.component.theme.Gray200
import info.imdang.component.theme.Gray400
import info.imdang.component.theme.Gray50
import info.imdang.component.theme.Gray700
import info.imdang.component.theme.Gray900
import info.imdang.component.theme.ImdangTheme
import info.imdang.component.theme.T500_16_22_4
import info.imdang.component.theme.T600_12_16_8
import info.imdang.component.theme.T600_16_22_4
import info.imdang.component.theme.T600_18_25_2
import info.imdang.component.theme.White
import info.imdang.resource.R
import java.io.File
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun WriteInsightBasicInfoPage(
    navController: NavController = rememberNavController(),
    viewModel: WriteInsightViewModel = hiltViewModel()
) {
    val focusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val isTitleFocused by viewModel.isTitleFocused.collectAsStateWithLifecycle()
    val isVisitDateFocused by viewModel.isVisitDateFocused.collectAsStateWithLifecycle()

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemScrollOffset }
            .collect {
                if (isTitleFocused || isVisitDateFocused) {
                    focusManager.clearFocus()
                }
            }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 20.dp,
            end = 20.dp,
            top = 24.dp,
            bottom = 40.dp
        ),
        verticalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        item {
            CoverImageView(viewModel = viewModel)
        }
        item {
            TitleView(
                viewModel = viewModel,
                onFocusChanged = { isFocused ->
                    coroutineScope.launch {
                        if (isFocused) listState.animateScrollToItem(index = 1)
                        viewModel.updateTitleFocused(isFocused)
                    }
                }
            )
        }
        item {
            AddressView()
        }
        item {
            VisitDateView(
                viewModel = viewModel,
                onFocusChanged = { isFocused ->
                    coroutineScope.launch {
                        if (isFocused) listState.animateScrollToItem(index = 3)
                        viewModel.updateVisitDateFocused(isFocused)
                    }
                }
            )
        }
        item {
            WriteInsightSelectionButtons(
                title = stringResource(R.string.visit_time),
                selectionItems = viewModel.visitTimes,
                onClick = {
                    coroutineScope.launch {
                        listState.animateScrollToItem(4)
                    }
                }
            )
        }
        item {
            WriteInsightSelectionButtons(
                title = stringResource(R.string.traffic_method),
                selectionItems = viewModel.trafficsMethods,
                onClick = {
                    coroutineScope.launch {
                        listState.animateScrollToItem(5)
                    }
                }
            )
        }
        item {
            WriteInsightSelectionButtons(
                title = stringResource(R.string.access_limit),
                selectionItems = viewModel.accessLimits,
                onClick = {
                    coroutineScope.launch {
                        listState.animateScrollToItem(6)
                    }
                }
            )
        }
        item {
            val summary by viewModel.summary.collectAsStateWithLifecycle()
            WriteInsightDetailContentView(
                title = stringResource(R.string.insight_summary),
                text = summary,
                hintText = stringResource(R.string.insight_summary_hint),
                isRequired = true,
                isValid = summary.length in 30..200,
                onClick = {
                    navController.navigate(WRITE_INSIGHT_SUMMARY_SCREEN)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CoverImageView(viewModel: WriteInsightViewModel) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val sheetState = rememberModalBottomSheetState()
    var isShowMediaBottomSheet by remember { mutableStateOf(false) }

    // PickMediaLauncher
    val pickMediaLauncher = rememberPickMediaLauncher { uri ->
        viewModel.updateCoverImageFile(getFileFromContentUri(context, uri))
    }

    // TakePictureLauncher
    var takePictureFile: File? by remember { mutableStateOf(null) }
    val takePictureLauncher = rememberTakePictureLauncher { isSuccess ->
        if (isSuccess) {
            viewModel.updateCoverImageFile(takePictureFile)
            takePictureFile = null
        }
    }
    val takePicture: () -> Unit = { takePictureLauncher.launch(context) { takePictureFile = it } }

    // PermissionLauncher
    var cameraPermissionGranted by rememberPermissionGranted(
        context,
        Manifest.permission.CAMERA
    )
    val permissionLauncher = rememberPermissionLauncher { isGranted ->
        cameraPermissionGranted = isGranted
        if (isGranted) takePicture()
    }

    if (isShowMediaBottomSheet) {
        ModalBottomSheet(
            sheetState = sheetState,
            shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
            containerColor = White,
            dragHandle = null,
            onDismissRequest = { isShowMediaBottomSheet = false }
        ) {
            MediaBottomSheet(
                sheetState = sheetState,
                onCloseBottomSheet = { isShowMediaBottomSheet = false },
                onClickSelectFromAlbum = {
                    isShowMediaBottomSheet = false
                    pickMediaLauncher.launch()
                },
                onClickTakePicture = {
                    isShowMediaBottomSheet = false
                    if (!cameraPermissionGranted) {
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    } else {
                        takePicture()
                    }
                }
            )
        }
    }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        WriteInsightTitle(
            title = stringResource(R.string.cover_image),
            isRequired = true
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val coverImage by viewModel.coverImageFile.collectAsStateWithLifecycle()
            Box {
                Box(
                    modifier = Modifier
                        .width(140.dp)
                        .height(100.dp)
                        .background(color = Gray50, shape = RoundedCornerShape(4.dp))
                        .border(width = 1.dp, color = Gray100, shape = RoundedCornerShape(4.dp))
                        .clip(RoundedCornerShape(4.dp))
                        .clickable {
                            focusManager.clearFocus()
                            isShowMediaBottomSheet = true
                        }
                ) {
                    Icon(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(20.dp),
                        iconResource = R.drawable.ic_image,
                        tint = Gray200
                    )
                }
                Image(
                    modifier = Modifier
                        .visible(coverImage != null)
                        .width(140.dp)
                        .height(100.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    painter = rememberAsyncImagePainter(
                        model = coverImage,
                        contentScale = ContentScale.Crop
                    ),
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
            }
            Row(
                modifier = Modifier
                    .height(32.dp)
                    .background(color = White, shape = RoundedCornerShape(6.dp))
                    .border(width = 1.dp, color = Gray100, shape = RoundedCornerShape(6.dp))
                    .clip(RoundedCornerShape(6.dp))
                    .clickable {
                        focusManager.clearFocus()
                        isShowMediaBottomSheet = true
                    }
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .visible(coverImage == null)
                        .size(12.dp),
                    iconResource = R.drawable.ic_plus,
                    tint = Gray700
                )
                Text(
                    text = stringResource(
                        if (coverImage == null) R.string.image_add else R.string.image_edit
                    ),
                    style = T600_12_16_8,
                    color = Gray700
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MediaBottomSheet(
    sheetState: SheetState,
    onCloseBottomSheet: () -> Unit,
    onClickSelectFromAlbum: () -> Unit,
    onClickTakePicture: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .background(White)
            .padding(start = 20.dp, end = 17.dp, top = 21.dp, bottom = 40.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.image_add),
                style = T600_18_25_2,
                color = Gray900
            )
            Icon(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        coroutineScope.launch {
                            sheetState.hide()
                            onCloseBottomSheet()
                            delay(100)
                        }
                    }
                    .padding(all = 6.dp)
                    .size(20.dp),
                iconResource = R.drawable.ic_close,
                tint = Gray900
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .border(width = 1.dp, color = Gray100, shape = RoundedCornerShape(8.dp))
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        coroutineScope.launch {
                            sheetState.hide()
                            onClickSelectFromAlbum()
                            delay(100)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.select_from_album),
                    style = T600_16_22_4,
                    color = Gray700
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(color = White, shape = RoundedCornerShape(8.dp))
                    .border(width = 1.dp, color = Gray100, shape = RoundedCornerShape(8.dp))
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        coroutineScope.launch {
                            sheetState.hide()
                            onClickTakePicture()
                            delay(100)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.take_picture),
                    style = T600_16_22_4,
                    color = Gray700
                )
            }
        }
    }
}

@Composable
private fun TitleView(
    viewModel: WriteInsightViewModel,
    onFocusChanged: (isFocused: Boolean) -> Unit
) {
    CommonTextField(
        modifier = Modifier
            .focusRequester(FocusRequester())
            .onFocusChanged {
                onFocusChanged(it.isFocused)
            },
        text = viewModel.title.collectAsStateWithLifecycle().value,
        imeAction = ImeAction.Next,
        title = stringResource(R.string.title),
        isRequired = true,
        minLength = 1,
        maxLength = 10,
        onTextChanged = {
            viewModel.updateTitle(title = it)
        }
    )
}

@Composable
private fun AddressView() {
    val focusManager = LocalFocusManager.current

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        WriteInsightTitle(title = stringResource(R.string.complex_address), isRequired = true)
        Box(
            modifier = Modifier
                .height(52.dp)
                .background(color = White, shape = RoundedCornerShape(8.dp))
                .border(width = 1.dp, color = Gray100, shape = RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .clickable {
                    focusManager.clearFocus()
                    // todo : 지번 검색
                }
                .padding(horizontal = 16.dp)
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth(),
                text = stringResource(R.string.jibun_address),
                style = T500_16_22_4,
                color = Gray400
            )
        }
        Box(
            modifier = Modifier
                .height(52.dp)
                .background(color = White, shape = RoundedCornerShape(8.dp))
                .border(width = 1.dp, color = Gray100, shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 16.dp)
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth(),
                text = stringResource(R.string.complex_name),
                style = T500_16_22_4,
                color = Gray400
            )
        }
    }
}

@Composable
private fun VisitDateView(
    viewModel: WriteInsightViewModel,
    onFocusChanged: (isFocused: Boolean) -> Unit
) {
    CommonTextField(
        modifier = Modifier
            .focusRequester(FocusRequester())
            .onFocusChanged {
                onFocusChanged(it.isFocused)
            },
        text = viewModel.visitDate.collectAsStateWithLifecycle().value,
        hintText = stringResource(R.string.visit_date_hint),
        errorText = viewModel.visitDateErrorMessage.collectAsStateWithLifecycle().value,
        keyboardType = KeyboardType.Number,
        imeAction = ImeAction.Done,
        title = stringResource(R.string.visit_date),
        maxLength = 10,
        isRequired = true,
        onTextChanged = {
            viewModel.updateVisitDate(visitDate = it)
        }
    )
}

@Preview(showBackground = true, heightDp = 1410)
@Composable
private fun WriteInsightBasicInfoPreview() {
    WriteInsightInit()
    ImdangTheme {
        WriteInsightBasicInfoPage()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun MediaBottomSheetPreview() {
    ImdangTheme {
        MediaBottomSheet(
            sheetState = rememberModalBottomSheetState(),
            onCloseBottomSheet = {},
            onClickSelectFromAlbum = {},
            onClickTakePicture = {}
        )
    }
}
