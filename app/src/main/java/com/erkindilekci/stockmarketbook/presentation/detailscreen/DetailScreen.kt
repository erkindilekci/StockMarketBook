package com.erkindilekci.stockmarketbook.presentation.detailscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.erkindilekci.stockmarketbook.presentation.detailscreen.components.StockChart
import com.erkindilekci.stockmarketbook.presentation.ui.theme.BackgroundColor
import com.erkindilekci.stockmarketbook.presentation.ui.theme.CardColor
import com.erkindilekci.stockmarketbook.presentation.util.ErrorScreen
import com.erkindilekci.stockmarketbook.presentation.util.LoadingScreen

@Composable
fun DetailScreen(
    viewModel: DetailScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state

    if (state.error == null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundColor)
                .padding(16.dp)
        ) {
            state.companyDetail?.let { company ->
                Card(
                    modifier = Modifier.padding(start = 4.dp, bottom = 8.dp, end = 4.dp),
                    shape = RoundedCornerShape(10.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(CardColor)
                            .padding(8.dp)
                    ) {
                        Text(
                            text = company.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.fillMaxWidth(),
                            color = Color.LightGray
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = company.symbol,
                            fontStyle = FontStyle.Italic,
                            fontSize = 14.sp,
                            modifier = Modifier.fillMaxWidth(),
                            color = Color.LightGray
                        )
                    }
                }

                Card(
                    modifier = Modifier.padding(start = 4.dp, bottom = 8.dp, end = 4.dp),
                    shape = RoundedCornerShape(10.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(CardColor)
                            .padding(8.dp)
                    ) {
                        Text(
                            text = "Industry: ${company.industry}",
                            fontSize = 14.sp,
                            modifier = Modifier.fillMaxWidth(),
                            overflow = TextOverflow.Ellipsis,
                            color = Color.LightGray
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Country: ${company.country}",
                            fontSize = 14.sp,
                            modifier = Modifier.fillMaxWidth(),
                            overflow = TextOverflow.Ellipsis,
                            color = Color.LightGray
                        )
                    }
                }

                Card(
                    modifier = Modifier.padding(start = 4.dp, bottom = 8.dp, end = 4.dp),
                    shape = RoundedCornerShape(10.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(CardColor)
                            .padding(8.dp)
                    ) {
                        Text(
                            text = company.description,
                            fontSize = 12.sp,
                            modifier = Modifier.fillMaxWidth(),
                            color = Color.LightGray
                        )
                    }
                }

                Card(
                    modifier = Modifier.padding(start = 4.dp, bottom = 8.dp, end = 4.dp),
                    shape = RoundedCornerShape(10.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(CardColor)
                    ) {
                        if (state.stockDetailList.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Market Summary",
                                modifier = Modifier.align(CenterHorizontally),
                                color = Color.LightGray
                            )
                            Spacer(modifier = Modifier.height(32.dp))
                            StockChart(
                                infoList = state.stockDetailList,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(250.dp)
                                    .padding(8.dp)
                                    .align(CenterHorizontally)
                            )
                        }
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Center
    ) {
        if (state.isLoading) {
            LoadingScreen()
        } else if (state.error != null) {
            ErrorScreen(error = state.error)
        }
    }
}
