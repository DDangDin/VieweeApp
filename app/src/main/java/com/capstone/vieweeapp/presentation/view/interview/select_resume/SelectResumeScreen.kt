package com.capstone.vieweeapp.presentation.view.interview.select_resume

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.vieweeapp.R
import com.capstone.vieweeapp.data.source.local.entity.Resume
import com.capstone.vieweeapp.presentation.event.SelectResumeUiEvent
import com.capstone.vieweeapp.presentation.state.ResumeState
import com.capstone.vieweeapp.ui.theme.VieweeColorBackgroundGrey
import com.capstone.vieweeapp.ui.theme.VieweeColorMain
import com.capstone.vieweeapp.ui.theme.VieweeColorOrange
import com.capstone.vieweeapp.ui.theme.VieweeColorText
import com.capstone.vieweeapp.ui.theme.noToSansKr
import com.capstone.vieweeapp.utils.Constants
import com.capstone.vieweeapp.utils.CustomRippleEffect.clickableWithoutRipple
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectResumeScreen(
    modifier: Modifier = Modifier,
    resumeState: ResumeState,
    onFinish: () -> Unit,
    onAddResume: () -> Unit,
    selectResumeUiEvent: (SelectResumeUiEvent) -> Unit,
    prepareInterview: () -> Unit
) {

    val resumes = resumeState.resumes

    // 선택한 자기소개서 index가 됨
    var selectedIndex by remember { mutableStateOf(-1) }
    var longClickSelectedIndex by remember { mutableStateOf(-1) }
    var isLongClick by remember { mutableStateOf(false) }

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    // BottomSheet
    if (showBottomSheet) {
        ModalBottomSheet(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState,
            containerColor = Color(0xFFF5F6F8),
            contentColor = Color(0xFFF5F6F8)
        ) {
            SelectBottomSheet(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(bottom = 30.dp),
                backgroundColor = Color(0xFFF5F6F8),
                prepareInterview = { engine ->
                    scope.launch {
                        sheetState.hide()
                        prepareInterview()
                    }
                }
            )
        }
    }


    Box(modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(10.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- TopBar(start) ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    modifier = Modifier
                        .size(50.dp),
                    onClick = onFinish
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "뒤로가기",
                        tint = VieweeColorText.copy(0.8f)
                    )
                }
                Text(
                    text = stringResource(id = R.string.select_resume_screen_title),
                    modifier = Modifier
                        .padding(15.dp),
                    fontFamily = noToSansKr,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = VieweeColorMain.copy(0.8f),
                    style = TextStyle(
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        )
                    )
                )
                IconButton(
                    modifier = Modifier
                        .size(50.dp),
                    onClick = onAddResume
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "추가",
                        tint = VieweeColorText.copy(0.8f)
                    )
                }
            }
            // --- TopBar(end) ---
            LazyColumn(
                modifier = Modifier.padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(
                    17.dp,
                    alignment = Alignment.CenterVertically
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(vertical = 30.dp)
            ) {
                items(resumes.size) { idx ->
                    val borderColor =
                        if ((idx == selectedIndex)) VieweeColorMain.copy(alpha = 0.8f) else VieweeColorMain.copy(
                            0.4f
                        )
                    val borderStroke =
                        if ((idx == selectedIndex)) 2.dp else 1.3.dp
                    val backgroundColor =
                        if (idx == selectedIndex) {
                            VieweeColorMain.copy(0.1f)
                        } else if(isLongClick && idx == longClickSelectedIndex){
                            Color.Gray
                        } else {
                            VieweeColorBackgroundGrey
                        }

                    ResumeCardView(
                        modifier = Modifier
                            .border(borderStroke, borderColor, RoundedCornerShape(10.dp))
                            .background(backgroundColor, RoundedCornerShape(10.dp)),
                        resume = resumes[idx],
                        onClick = {
                            if (selectedIndex == idx || isLongClick) {
                                selectedIndex = -1
                            } else {
                                selectedIndex = idx
                            }
                            isLongClick = false
                            longClickSelectedIndex = -1
                        },
                        onLongClick = {
                            isLongClick = true
                            longClickSelectedIndex = idx
                        },
                        idx = idx
                    )
                }
            }
        }

        AnimatedVisibility(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 50.dp),
            visible = selectedIndex != -1,
            enter = slideInVertically() + fadeIn(),
            exit = slideOutVertically() + fadeOut(),
        ) {
            ShowNextButton(
                onClick = {
                    Log.d("ShowNextButton_Click_Log", "selectedIndex: $selectedIndex")
                    selectResumeUiEvent(SelectResumeUiEvent.SelectedResume(resumes[selectedIndex]))
                    showBottomSheet = true
                }
            )
        }
        AnimatedVisibility(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 50.dp),
            visible = isLongClick,
            enter = slideInVertically() + fadeIn(),
            exit = slideOutVertically() + fadeOut(),
        ) {
            ShowDeleteButton(
                onClick = {
                    Log.d("ShowNextButton_Click_Log", "selectedIndex: $longClickSelectedIndex")
                    selectResumeUiEvent(SelectResumeUiEvent.DeleteResume(resumes[longClickSelectedIndex]))
                    isLongClick = false
                    longClickSelectedIndex = -1
                }
            )
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ResumeCardView(
    modifier: Modifier = Modifier,
    resume: Resume,
    idx: Int,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {

    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .animateContentSize(
                animationSpec = tween(100, easing = FastOutSlowInEasing)
            )
            .clip(RoundedCornerShape(10.dp))
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick,
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(7.dp, alignment = Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ResumeCardViewTopBar(
                modifier = Modifier.fillMaxWidth(),
                changeExpanded = { expanded = !expanded },
                expanded = expanded,
                resumeName = "${resume.name} ${idx + 1}",
            )
            androidx.compose.material.Text(
                modifier = Modifier.fillMaxWidth(),
                text = resume.resumeDetail.resumeText,
                fontSize = 13.sp,
                textAlign = TextAlign.Left,
                color = VieweeColorText,
                maxLines = if (expanded) 100 else 3,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 19.sp
            )
        }
    }
}

@Composable
fun ResumeCardViewTopBar(
    modifier: Modifier = Modifier,
    changeExpanded: () -> Unit,
    expanded: Boolean,
    resumeName: String,
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(
                7.dp,
                alignment = Alignment.CenterHorizontally
            )
        ) {
            Text(
                text = resumeName,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = VieweeColorMain,
            )
        }
        IconButton(
            modifier = Modifier.size(30.dp),
            onClick = { changeExpanded() }
        ) {
            Icon(
                modifier = Modifier.size(30.dp),
                imageVector = if (expanded) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
                contentDescription = if (expanded) {
                    "더보기"
                } else {
                    "요약하기"
                },
                tint = Color(0xFF92979F).copy(0.8f)
            )
        }
    }
}

@Composable
fun ShowNextButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier
            .background(VieweeColorMain, CircleShape)
            .clip(CircleShape)
            .size(65.dp),
        onClick = onClick
    ) {
        Icon(
            modifier = Modifier.size(40.dp),
            imageVector = Icons.Filled.Check,
            contentDescription = "확인",
            tint = Color.White
        )
    }
}

@Composable
fun ShowDeleteButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier
            .background(VieweeColorOrange, CircleShape)
            .clip(CircleShape)
            .size(65.dp),
        onClick = onClick
    ) {
        Icon(
            modifier = Modifier.size(40.dp),
            imageVector = ImageVector.vectorResource(R.drawable.ic_exit),
            contentDescription = "확인",
            tint = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SelectResumeScreenPreview() {
    SelectResumeScreen(
        resumeState = ResumeState(
            resumes = listOf(Constants.RESUME_DUMMY_DATA)
        ),
        onFinish = { /*TODO*/ },
        onAddResume = { /*TODO*/ },
        selectResumeUiEvent = {},
        prepareInterview = {}
    )
}