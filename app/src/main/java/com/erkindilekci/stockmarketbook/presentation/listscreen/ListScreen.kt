package com.erkindilekci.stockmarketbook.presentation.listscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erkindilekci.stockmarketbook.R
import com.erkindilekci.stockmarketbook.presentation.listscreen.components.ListScreenItemCard
import com.erkindilekci.stockmarketbook.presentation.ui.theme.CardColor
import com.erkindilekci.stockmarketbook.presentation.util.ErrorScreen
import com.erkindilekci.stockmarketbook.presentation.util.LoadingScreen
import com.erkindilekci.stockmarketbook.util.Screen
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun ListScreen(
    viewModel: ListScreenViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.state
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = state.isRefreshing)

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = { viewModel.onEvent(ListScreenEvent.Refresh) }
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                OutlinedTextField(
                    value = state.searchQuery,
                    onValueChange = {
                        viewModel.onEvent(
                            ListScreenEvent.OnSearchQueryChange(it)
                        )
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(60)),
                    placeholder = {
                        Text(stringResource(id = R.string.search))
                    },
                    maxLines = 1,
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledContainerColor = CardColor,
                        focusedContainerColor = CardColor,
                        unfocusedContainerColor = CardColor,
                        disabledLabelColor = Color.Transparent,
                        focusedLabelColor = Color.Transparent,
                        unfocusedLabelColor = Color.Transparent,
                        disabledBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent,
                        unfocusedTextColor = Color.LightGray,
                        focusedTextColor = Color.LightGray,
                        disabledTextColor = Color.LightGray
                    ),
                    trailingIcon = {
                        if (state.searchQuery.isNotEmpty()) {
                            Icon(
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .clickable {
                                        viewModel.onEvent(
                                            ListScreenEvent.OnSearchQueryChange("")
                                        )
                                    },
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = Color.LightGray
                            )
                        }
                    },
                )
            }

            items(state.companies) { company ->
                ListScreenItemCard(
                    company = company,
                    onItemClicked = {
                        navController.navigate(Screen.DetailScreen.route + "/${company.symbol}")
                    }
                )
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (state.isLoading) {
                LoadingScreen()
            } else if (state.error != "") {
                ErrorScreen(error = state.error)
            }
        }
    }
}
