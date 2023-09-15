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
    object Home: Screen("home_screen", "홈", R.drawable.ic_nav_item_home)
    object Interview: Screen("interview_screen", "면접", R.drawable.ic_nav_item_interview)
    object Calendar: Screen("calendar_screen", "캘린더", R.drawable.ic_nav_item_calendar)
    object Profile: Screen("profile_screen", "프로필", R.drawable.ic_nav_item_profile)
}

val bottomNavItems = listOf(
    Screen.Home,
    Screen.Interview,
    Screen.Calendar,
    Screen.Profile,
)