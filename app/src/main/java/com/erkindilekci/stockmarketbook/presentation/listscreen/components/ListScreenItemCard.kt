package com.erkindilekci.stockmarketbook.presentation.listscreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erkindilekci.stockmarketbook.domain.model.Company
import com.erkindilekci.stockmarketbook.presentation.ui.theme.CardColor

@Composable
fun ListScreenItemCard(
    company: Company,
    onItemClicked: (Company) -> Unit
) {
    Card(
        modifier = Modifier.padding(start = 16.dp, bottom = 16.dp, end = 16.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(CardColor)
                .clickable { onItemClicked(company) },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f), verticalArrangement = Arrangement.Top
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = company.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.LightGray,
                        fontWeight = FontWeight.Medium,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        modifier = Modifier
                            .weight(1f)
                            .padding(top = 5.dp, start = 10.dp)
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = company.exchange,
                        style = MaterialTheme.typography.titleSmall,
                        fontSize = 15.sp,
                        color = Color.LightGray,
                        modifier = Modifier.padding(top = 5.dp, end = 10.dp)
                    )
                }


                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "(${company.symbol})",
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.LightGray,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = 5.dp, start = 10.dp)
                )
            }
        }
    }
}
