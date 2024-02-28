package com.biho.library.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.biho.library.LibraryScreen
import com.biho.library.LibraryViewModel
import com.biho.login.EditProfileScreen
import com.biho.login.EditProfileViewModel
import com.biho.login.LoginScreen
import com.biho.login.LoginViewModel
import com.biho.pixify.core.model.danbooru.model.profile.Profile
import com.biho.product.composables.ScreenUnderConstruction
import com.biho.ui.screen.LibraryRoute
import com.biho.ui.screen.MainRoute
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.libraryRoute(navController: NavHostController) {

    navigation(
        startDestination = LibraryRoute.Library.route,
        route = MainRoute.Library.route
    ) {
        composable(route = LibraryRoute.Library.route) {
            val navigateToProfileScreen = { roomId: Int? ->
                navController.navigate(LibraryRoute.Profile.route)
            }
            val navigateToCreateProfileScreen = {
                navController.navigate(LibraryRoute.CreateProfile.route)
            }
            val navigateToEditProfileScreen = { roomId: Int? ->
                navController.navigate(LibraryRoute.EditProfile.route)
            }
            val onCategoryItemClick = { route: String ->
                navController.navigate(route)
            }
            val libraryViewModel = koinViewModel<LibraryViewModel>()
            val deleteProfile = { user: Profile ->
                libraryViewModel.deleteProfile(user)
            }
            val selectProfile = { roomId: Int? ->
                libraryViewModel.switchToProfile(roomId)
            }
            val profiles by libraryViewModel.profiles.collectAsState()
            LibraryScreen(
                profiles = profiles,
                navigateToProfileScreen = navigateToProfileScreen,
                createProfile = navigateToCreateProfileScreen,
                selectProfile = selectProfile,
                editProfile = navigateToEditProfileScreen,
                deleteProfile = deleteProfile,
                onCategoryItemClick = onCategoryItemClick
            )
        }
        composable(route = LibraryRoute.Profile.route) {
            ScreenUnderConstruction()
        }
        composable(route = LibraryRoute.Favourites.route) {
            ScreenUnderConstruction()
        }
        composable(route = LibraryRoute.Bookmarks.route) {
            ScreenUnderConstruction()
        }
        composable(route = LibraryRoute.Settings.route) {
            ScreenUnderConstruction()
        }
        composable(route = LibraryRoute.CreateProfile.route) {
            val loginViewModel = koinViewModel<LoginViewModel>()
            val navigateToProfile = {
                navController.popBackStack()
                navController.navigate(LibraryRoute.Profile.route)
            }
            val updateUsername = { value: TextFieldValue ->
                loginViewModel.updateUsername(value)
            }
            val updateApiKey = { value: TextFieldValue ->
                loginViewModel.updateApiKey(value)
            }
            LoginScreen(
                loginState = loginViewModel.loginStatus,
                username = loginViewModel.username,
                apiKey = loginViewModel.apiKey,
                onNavigateBack = { navController.popBackStack() },
                navigateToProfile = navigateToProfile,
                updateUsername = updateUsername,
                updateApiKey = updateApiKey,
                login = { loginViewModel.performLogin() }
            )
        }
        composable(route = LibraryRoute.EditProfile.route) {
            val editProfileViewModel = koinViewModel<EditProfileViewModel>()
            val updateUsername = { value: TextFieldValue ->
                editProfileViewModel.updateUsername(value.text)
            }
            val updateApiKey = { value: TextFieldValue ->
                editProfileViewModel.updateApiKey(value.text)
            }
            EditProfileScreen(
                profileEditField = editProfileViewModel.profileEditField,
                onUsernameChanged = updateUsername,
                onApiKeyChanged = updateApiKey,
                confirm = {},
                onNavigateBack = { navController.popBackStack() }
            )
        }

    }
}