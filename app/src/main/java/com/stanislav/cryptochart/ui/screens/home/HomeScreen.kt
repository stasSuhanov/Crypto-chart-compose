package com.stanislav.cryptochart.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.stanislav.cryptochart.R
import com.stanislav.cryptochart.domain.entity.CoinEntity
import com.stanislav.cryptochart.utils.roundTo

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    when (uiState) {
        is HomeViewState.Success -> CoinsContent(
            coins = (uiState as HomeViewState.Success).coins
        ) { navController.navigate("coinDetails/${it}") }
        is HomeViewState.Loading -> LoadingView()
        is HomeViewState.Error -> ErrorView()
    }
}

@Composable
fun CoinsContent(
    coins: List<CoinEntity>,
    selectCoin: (String) -> Unit
) {
    val scrollState = rememberLazyListState()

    LazyColumn(state = scrollState) {
        items(coins.size) { index ->
            CoinItemModel(
                coins[index],
                selectCoin
            )
        }
    }
}

@Composable
fun CoinItemModel(
    model: CoinEntity,
    onCoinClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
                onCoinClick(model.symbol)
            },
        shape = RoundedCornerShape(10.dp),
        elevation = 4.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = model.cmcRank,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = model.symbol,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = model.name,
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
            Text(
                text = model.price.roundTo(2).toString(),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(id = R.string.home_screen_percent_change, model.percentChange24h.roundTo(1).toString()),
                color = setColor(model.percentChange24h)
            )
        }
    }
}

@Composable
fun ErrorView() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = stringResource(id = R.string.home_screen_error))
    }
}

@Composable
fun LoadingView() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}

fun setColor(percentChange24h: Double): Color = if (percentChange24h > 0) {
    Color.Green
} else {
    Color.Red
}