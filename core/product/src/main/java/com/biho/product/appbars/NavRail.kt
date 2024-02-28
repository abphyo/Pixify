package com.biho.product.appbars

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.biho.resources.theme.bottomNavBarDark
import com.biho.resources.theme.bottomNavBarLight
import com.biho.ui.screen.MainRoute

@Composable
fun NavRail(
    navHostController: NavHostController,
    onItemClick: (String) -> Unit,
    onItemReSelected: () -> Unit
) {
    NavigationRail(
        header = {
            Box {
                Image(
                    painter = painterResource(id = com.biho.resources.R.drawable.box),
                    contentDescription = "app_icon"
                )
            }
        },
        containerColor = when {
            isSystemInDarkTheme() -> bottomNavBarDark.copy(alpha = 0.95f)
            else -> bottomNavBarLight.copy(alpha = 0.9f)
        },
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.inverseOnSurface)
            .offset(x = (-1).dp)
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically)
        ) {
            MainNavItem.entries.forEach {
                val navBackStackEntry by navHostController.currentBackStackEntryAsState()
                val selected = it.route == navBackStackEntry?.destination?.parent?.route
                NavigationRailItem(
                    selected = selected,
                    enabled = true,
                    onClick = {
                        when {
                            it.route == MainRoute.Home.route && selected -> onItemReSelected()
                            else -> onItemClick(it.route)
                        }
                    },
                    icon = {
                        MainNavIcon(item = it, isSelected = selected)
                    },
                    label = { Text(text = it.label) }
                )
            }
        }
    }
}