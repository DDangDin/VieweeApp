package com.capstone.vieweeapp.navigation.nav

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.capstone.vieweeapp.data.source.local.entity.InterviewResult
import com.capstone.vieweeapp.navigation.bottomNavItems
import com.capstone.vieweeapp.navigation.graph.BottomNavigationGraph
import com.capstone.vieweeapp.ui.theme.VieweeColorGray
import com.capstone.vieweeapp.ui.theme.VieweeColorMain
import com.capstone.vieweeapp.ui.theme.VieweeColorShadow
import com.capstone.vieweeapp.utils.Constants
import com.capstone.vieweeapp.utils.CustomRippleEffect

@Composable
fun VieweeBottomNavigation(
    navController: NavHostController,
    startSelectResume: () -> Unit,
    onStartReInterview: (Int) -> Unit
) {

    val context = LocalContext.current

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            Log.d("navBackStackEntry(currentRoute)", "currentRoute: $currentRoute")
            AnimatedVisibility(
                visible = bottomNavItems.map { it.route }.contains(currentRoute),
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it })
            ) {
                MyBottomBar(navController)
            }
        },
    ) {
        BottomNavigationGraph(
            modifier = Modifier.padding(it),
            navController = navController,
            startSelectResume = startSelectResume,
            onStartReInterview = { id -> onStartReInterview(id) }
        )
    }
}

@Composable
fun MyBottomBar(
    navController: NavHostController
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BottomAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(Constants.BOTTOM_NAV_BAR_PADDING.dp)
            .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
            .shadow(
//                shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp),
                elevation = 15.dp,
                spotColor = VieweeColorShadow,
                ambientColor = VieweeColorShadow
            ),
        cutoutShape = CircleShape,
        backgroundColor = Color.White,
        contentColor = Color.White
    ) {
        bottomNavItems.forEachIndexed { index, item ->
            CompositionLocalProvider(LocalRippleTheme provides CustomRippleEffect.NoRippleTheme) {
                BottomNavigationItem(
                    selected = (currentRoute == item.route),
                    onClick = {
                        navController.navigate(item.route) {
                            navController.graph.startDestinationRoute?.let {
                                // 첫번째 화면만 스택에 쌓이게 -> 백버튼 클릭 시 첫번째 화면으로 감
                                popUpTo(it) { saveState = true }
                            }
                            launchSingleTop = true  // 화면 인스턴스 하나만 만들어지게
                            restoreState = true     // 버튼을 재클릭했을 때 이전 상태가 남아있게
                        }
                    },
                    icon = {
                        Icon(
                            modifier = Modifier.size(25.dp),
                            imageVector = ImageVector.vectorResource(item.icon!!),
                            contentDescription = "item",
                            tint = if (currentRoute == item.route) {
                                VieweeColorMain
                            } else {
                                VieweeColorGray
                            }
                        )
                    },
//                    label = {
//                        Text(
//                            text = item.title!!,
//                            fontSize = 10.sp,
//                            color = if (currentRoute == item.route) {
//                                VieweeMainColor.copy(alpha = 0.8f)
//                            } else {
//                                VieweeMainColor
//                            }
//                        )
//                    },
                    enabled = true,
                    interactionSource = remember { MutableInteractionSource() },
                )
            }
        }
    }
}

@Preview
@Composable
fun BottomNavigationPreview() {
    VieweeBottomNavigation(
        navController = rememberNavController(),
        startSelectResume = {},
        onStartReInterview = {}
    )
}