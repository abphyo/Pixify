package com.biho.product.appbars

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.IntState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.biho.resources.theme.DIMENS_12dp
import com.biho.resources.theme.LocalLexFontFamily
import com.biho.resources.theme.bottomNavBarLight
import com.biho.resources.theme.bottomNavItemActive
import com.biho.resources.theme.bottomNavItemInActive
import com.biho.ui.screen.MainRoute
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Parabolic
import com.exyte.animatednavbar.animation.indendshape.Height
import com.exyte.animatednavbar.animation.indendshape.shapeCornerRadius
import com.exyte.animatednavbar.utils.noRippleClickable

@Composable
fun AnimatedNavBar(
    navHostController: NavHostController,
    onItemClick: (String) -> Unit,
    onItemReselected: () -> Unit
) {
    var selectedIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    AnimatedNavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(DIMENS_12dp),
        selectedIndex = selectedIndex,
        barColor = BottomAppBarDefaults.containerColor.copy(alpha = 0.95f),
        ballColor = bottomNavBarLight,
        ballAnimation = Parabolic(tween(300)),
        indentAnimation = Height(tween(300)),
        cornerRadius = shapeCornerRadius(cornerRadius = 40.dp)
    ) {
        val navBackStackEntry by navHostController.currentBackStackEntryAsState()
        MainNavItem.entries.forEach {
            val isSelected = it.route == navBackStackEntry?.destination?.parent?.route
            if (isSelected) selectedIndex = it.ordinal
            Box(
                modifier = Modifier.padding(5.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.noRippleClickable {
                        when {
                            it.route == MainRoute.Home.route && isSelected -> onItemReselected()
                            else -> onItemClick(it.route)
                        }
                    },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MainNavIcon(item = it, isSelected = isSelected, size = 32.dp)
                    Text(
                        text = it.label,
                        color = when {
                            isSelected -> bottomNavItemActive
                            else -> bottomNavItemInActive
                        },
                        fontFamily = LocalLexFontFamily.current,
                        fontWeight = FontWeight.Light,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}