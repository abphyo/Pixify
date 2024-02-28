package com.biho.product.appbars

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.biho.resources.theme.bottomNavItemActive
import com.biho.resources.theme.bottomNavItemInActive

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavIcon(
    item: MainNavItem,
    isSelected: Boolean,
    size: Dp = 24.dp
) {
    BadgedBox(
        badge = {
            when {
                item.notificationCount != null -> Badge {
                    Text(text = item.notificationCount.toString())
                }

                item.hasNews -> Badge()
            }
        }
    ) {
        Icon(
            modifier = Modifier
                .wrapContentSize()
                .size(size = size),
            painter = painterResource(
                id = when {
                    isSelected -> item.activeIcon
                    else -> item.inActiveIcon
                }
            ),
            contentDescription = item.name,
            tint = when {
                isSelected -> bottomNavItemActive
                else -> bottomNavItemInActive
            }
        )
    }
}