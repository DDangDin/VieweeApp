package com.capstone.vieweeapp.presentation.view.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.capstone.vieweeapp.R
import com.capstone.vieweeapp.data.source.local.entity.InterviewResult
import com.capstone.vieweeapp.ui.theme.VieweeColorHomeText
import com.capstone.vieweeapp.ui.theme.noToSansKr
import com.capstone.vieweeapp.utils.Constants
import com.capstone.vieweeapp.utils.CustomRippleEffect.clickableWithoutRipple

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    username: String,
    interviewResults: List<InterviewResult>
) {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = Constants.BOTTOM_NAV_BAR_PADDING.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        HomeTopBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = Constants.HOME_PADDING_VALUE_HORIZONTAL.dp,
                    vertical = Constants.HOME_PADDING_VALUE_VERTICAL.dp
                ),
            logo = R.drawable.img_logo,
            onMenuClick = {}
        )
        Column(
            modifier = Modifier.verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(
                30.dp,
                alignment = Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Constants.HOME_PADDING_VALUE_HORIZONTAL.dp)
                    .wrapContentHeight(),
                verticalArrangement = Arrangement.spacedBy(
                    25.dp,
                    alignment = Alignment.CenterVertically
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HomeIntroductionText(
                    modifier = Modifier.fillMaxWidth(),
                    username = username
                )
                SearchBar(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = searchText,
                    onTextChanged = { onSearchTextChanged(it) }
                )
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = VieweeColorHomeText.copy(alpha = 0.3f)
                )
            }
            // Today's Calendar Section
            HomeTodayCalendar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Constants.HOME_PADDING_VALUE_HORIZONTAL.dp),
                username = username
            )
            // InterviewResult Section
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(20.dp, alignment = Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HomeInterviewResultText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Constants.HOME_PADDING_VALUE_HORIZONTAL.dp),
                    username = username,
                )
                if (interviewResults.isEmpty()) {
                    Text(
                        text = stringResource(id = R.string.home_text_interview_result_2),
                        fontFamily = noToSansKr,
                        fontWeight = FontWeight.Normal,
                        color = VieweeColorHomeText.copy(0.7f)
                    )
                } else {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(
                            22.dp,
                            alignment = Alignment.Start
                        ),
                        contentPadding = PaddingValues(horizontal = Constants.HOME_PADDING_VALUE_HORIZONTAL.dp)
                    ) {
                        itemsIndexed(interviewResults) { index, interviewResult ->
                            InterviewResultCardView(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(15.dp))
                                    .clickable {  },
                                index = index,
                                listSize = interviewResults.size,
                                interviewResult = interviewResult
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        searchText = "",
        onSearchTextChanged = {},
        username = "김길동",
        interviewResults = Constants.getInterviewResultList()
    )
}