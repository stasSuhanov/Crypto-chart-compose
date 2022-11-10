package com.stanislav.cryptochart.ui.screens.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.stanislav.cryptochart.R
import com.stanislav.cryptochart.ui.screens.details.view.CoinDetailsModel
import com.stanislav.cryptochart.ui.screens.home.ErrorView
import com.stanislav.cryptochart.ui.screens.home.LoadingView
import com.stanislav.cryptochart.utils.roundTo

@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    when (uiState) {
        is DetailsViewState.Success -> CoinContent(model = (uiState as DetailsViewState.Success).coin)
        is DetailsViewState.Loading -> LoadingView()
        is DetailsViewState.Error -> ErrorView()
    }
}

@Composable
fun CoinContent(
    model: CoinDetailsModel,
) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize(),
        shape = RoundedCornerShape(10.dp),
        elevation = 4.dp
    ) {
        Box {
            Column {
                Row {
                    AsyncImage(
                        model = model.logo,
                        contentDescription = stringResource(id = R.string.details_screen_coin_image),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(100.dp)
                            .height(100.dp)
                            .padding(top = 16.dp, start = 16.dp)
                    )
                    Column {
                        Text(
                            text = model.symbol,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = stringResource(id = R.string.details_screen_price, model.price.roundTo(2).toString()),
                            fontSize = 16.sp,
                            modifier = Modifier.padding(start = 16.dp, top = 4.dp),
                        )
                    }
                }
                Text(
                    text = stringResource(id = R.string.details_screen_description),
                    fontSize = 18.sp,
                    modifier = Modifier.padding(start = 32.dp, top = 8.dp),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = model.description,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp)
                )
                Text(
                    text = stringResource(id = R.string.details_screen_general_info),
                    fontSize = 18.sp,
                    modifier = Modifier.padding(start = 32.dp, top = 8.dp),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = stringResource(id = R.string.details_screen_rank, model.cmcRank),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp)
                )
                Text(
                    text = stringResource(id = R.string.details_screen_name, model.name),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp)
                )
                Text(
                    text = stringResource(id = R.string.details_screen_market_cup, model.marketCap),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp)
                )
                Text(
                    text = stringResource(id = R.string.details_screen_volume, model.volume24),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp)
                )
            }
        }
    }
}