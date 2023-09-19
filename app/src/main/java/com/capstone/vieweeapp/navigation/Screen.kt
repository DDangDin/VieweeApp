package com.capstone.vieweeapp.navigation

import com.capstone.vieweeapp.R

sealed class Screen(val route: String, val title: String, val icon: Int?) {
    // BottomNavigation Screens
    object Home: Screen("home_screen", "홈", R.drawable.ic_nav_item_home)
    object Interview: Screen("interview_screen", "면접", R.drawable.ic_nav_item_interview)
    object Calendar: Screen("calendar_screen", "캘린더", R.drawable.ic_nav_item_calendar)
    object Profile: Screen("profile_screen", "프로필", R.drawable.ic_nav_item_profile)

    // Other Screens
    object Test: Screen("test_screen", "테스트", null)

    // InputProfileNavigation Screens
    object SelectResume: Screen("select_resume_screen", "자기소개서 선택", null)
    object InputProfileNavigation: Screen("input_profile_navigation", "정보입력 네비게이션", null)
    object InputProfileFirst: Screen("input_profile_screen_1", "정보입력-1", null)
    object InputProfileSecond: Screen("input_profile_screen_2", "정보입력-2", null)
    object InputProfileThird: Screen("input_profile_screen_3", "정보입력-3", null)
    object InputProfileFinish: Screen("input_profile_finish_screen", "정보입력 완료", null)

    // RealInterviewNavigation Screens
    object RealInterviewNavigation: Screen("real_interview_navigation", "실제 면접 네비게이션", null)
    object InterviewPrepare: Screen("interview_prepare_screen", "면접 준비(안내사항)", null)
    object InterviewLoading: Screen("interview_loading_screen", "면접 로딩(면접 데이터 준비)", null)
    object RealInterview: Screen("real_interview_screen", "실제 면접 진행", null)

    object FeedbackNavigation: Screen("feedback_navigation", "피드백 네비게이션", null)
    object Feedback: Screen("feedback_screen", "피드백", null)
}

val bottomNavItems = listOf(
    Screen.Home,
    Screen.Interview,
    Screen.Calendar,
    Screen.Profile,
)