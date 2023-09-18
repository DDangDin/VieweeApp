package com.capstone.vieweeapp.navigation

import android.graphics.drawable.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.capstone.vieweeapp.R

sealed class Screen(val route: String, val title: String, val icon: Int?) {
    // BottomNavigation Screens
    object Home: Screen("home_screen", "홈", R.drawable.ic_nav_item_home)
    object Interview: Screen("interview_screen", "면접", R.drawable.ic_nav_item_interview)
    object Calendar: Screen("calendar_screen", "캘린더", R.drawable.ic_nav_item_calendar)
    object Profile: Screen("profile_screen", "프로필", R.drawable.ic_nav_item_profile)

    // Other Screens
    object Test: Screen("test_screen", "테스트", null)

    // InterviewNavigation Screens
    object SelectResume: Screen("select_resume_screen", "자기소개서 선택", null)
    object InputProfileNavigation: Screen("input_profile_navigation", "정보입력 네비게이션", null)
    object InputProfileFirst: Screen("input_profile_screen_1", "정보입력-1", null)
    object InputProfileSecond: Screen("input_profile_screen_2", "정보입력-2", null)
    object InputProfileThird: Screen("input_profile_screen_3", "정보입력-3", null)
    object InputProfileFinish: Screen("input_profile_finish_screen", "정보입력 완료", null)
}

val bottomNavItems = listOf(
    Screen.Home,
    Screen.Interview,
    Screen.Calendar,
    Screen.Profile,
)