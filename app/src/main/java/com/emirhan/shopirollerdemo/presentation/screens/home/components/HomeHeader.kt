package com.emirhan.shopirollerdemo.presentation.screens.home.components

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.emirhan.shopirollerdemo.R
import com.emirhan.shopirollerdemo.core.Constants.Companion.APP_LOGO
import com.emirhan.shopirollerdemo.core.Constants.Companion.NAVIGATE_BACK
import com.emirhan.shopirollerdemo.core.Constants.Companion.PRODUCTS
import com.emirhan.shopirollerdemo.core.Constants.Companion.SEARCH_ICON
import com.emirhan.shopirollerdemo.core.Constants.Companion.SETTINGS_ICON
import com.emirhan.shopirollerdemo.core.Constants.Companion.MAIN_URL
import com.emirhan.shopirollerdemo.presentation.screens.Screen
import com.emirhan.shopirollerdemo.presentation.screens.home.HomeViewModel
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeHeader(
    paddingValues: PaddingValues,
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToProduct: (String) -> Unit,
    navController: NavHostController
) {
    val visibilityState = remember { MutableTransitionState(initialState = true) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val intent = remember { Intent(Intent.ACTION_VIEW, Uri.parse(MAIN_URL)) }

    LaunchedEffect(visibilityState.currentState) {
        if (visibilityState.currentState) {
            focusManager.clearFocus()
        } else {
            focusRequester.requestFocus()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.onSearchChanged("")
    }

    Row(
        modifier = Modifier
            .padding(bottom = 30.dp, top = 10.dp)
            .padding(top = paddingValues.calculateTopPadding()),
        horizontalArrangement = Arrangement.Center,
    ) {
        AnimatedVisibility(
            visibleState = visibilityState, enter = EnterTransition.None, exit = fadeOut(
                animationSpec = tween(500)
            )
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                IconButton(modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 10.dp)
                    .clip(CircleShape)
                    .background(if (MaterialTheme.colors.isLight) Color.LightGray else Color.DarkGray),
                    onClick = { visibilityState.targetState = false }) {
                    Icon(
                        imageVector = Icons.Default.Search, contentDescription = SEARCH_ICON
                    )
                }
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    painter = painterResource(id = if (MaterialTheme.colors.isLight) R.drawable.logo_dark else R.drawable.logo_light),
                    contentDescription = APP_LOGO
                )
                IconButton(modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 10.dp)
                    .clip(CircleShape)
                    .background(if (MaterialTheme.colors.isLight) Color.LightGray else Color.DarkGray),
                    onClick = { context.startActivity(intent) }) {
                    Icon(
                        imageVector = Icons.Default.ArrowForward, contentDescription = SETTINGS_ICON
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = !visibilityState.currentState,
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                IconButton(modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 10.dp, top = 5.dp)
                    .clip(CircleShape)
                    .background(if (MaterialTheme.colors.isLight) Color.LightGray else Color.DarkGray),
                    onClick = {
                        visibilityState.targetState = true
                    }) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = NAVIGATE_BACK
                    )
                }

                ExposedDropdownMenuBox(modifier = Modifier.align(Alignment.Center),
                    expanded = viewModel.expanded,
                    onExpandedChange = {}) {
                    OutlinedTextField(
                        modifier = Modifier
                            .widthIn(250.dp)
                            .focusRequester(focusRequester),
                        value = viewModel.searchText,
                        singleLine = true,
                        onValueChange = { viewModel.onSearchChanged(it) },

                        )
                    ExposedDropdownMenu(expanded = viewModel.expanded,
                        onDismissRequest = { viewModel.expanded = !viewModel.expanded }) {
                        viewModel.searchState.forEach { products ->
                            DropdownMenuItem(onClick = {
                                scope.launch {
                                    // Clear
                                    viewModel.expanded = false
                                    viewModel.onSearchChanged("")
                                    focusManager.clearFocus()
                                    products.id?.let { id -> navigateToProduct(id) }
                                }
                            }) {
                                val decimalFormatter = DecimalFormat().apply {
                                    isDecimalSeparatorAlwaysShown = false
                                }
                                val currency = Currency.getInstance(products.currency)
                                products.title?.let { title ->
                                    if (products.price != null) {
                                        Text(
                                            text = if (products.campaignPrice != 0.0 && products.campaignPrice != null) "$title (${
                                                decimalFormatter.format(
                                                    products.campaignPrice
                                                )
                                            } ${currency.symbol})"
                                            else "$title (${decimalFormatter.format(products.price)} ${currency.symbol})"
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                IconButton(modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 10.dp, top = 5.dp)
                    .clip(CircleShape)
                    .background(if (MaterialTheme.colors.isLight) Color.LightGray else Color.DarkGray),
                    onClick = {
                        if (viewModel.searchText.length > 2) {
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                key = PRODUCTS, value = viewModel.searchState.toList()
                            )
                            navController.navigate(Screen.ProductSearchScreen.route)
                        }
                    }) {
                    Icon(
                        imageVector = Icons.Default.Search, contentDescription = SEARCH_ICON
                    )
                }
            }
        }
    }
}