package com.stanislav.cryptochart.ui.screens.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stanislav.cryptochart.ARG_KEY_COIN_SYMBOL
import com.stanislav.cryptochart.data.repository.CoinsRepository
import com.stanislav.cryptochart.ui.screens.details.view.CoinDetailsModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val coinsRepository: CoinsRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _uiState: MutableStateFlow<DetailsViewState> = MutableStateFlow(DetailsViewState.Loading)
    val uiState: StateFlow<DetailsViewState> = _uiState

    init {
        val argument = savedStateHandle.get<String>(ARG_KEY_COIN_SYMBOL).orEmpty()
        fetchDetails(argument)
    }

    private fun fetchDetails(symbol: String) {
        _uiState.value = DetailsViewState.Loading
        viewModelScope.launch {
            try {
                val coins = coinsRepository.fetchCoins().first { it.symbol == symbol }
                val coinDetails = coinsRepository.fetchCoin(symbol)
                _uiState.value = DetailsViewState.Success(
                    CoinDetailsModel(
                        id = coins.id,
                        name = coins.name,
                        symbol = coins.symbol,
                        slug = coins.slug,
                        cmcRank = coins.cmcRank,
                        price = coins.price,
                        marketCap = coins.marketCap,
                        percentChange24h = coins.percentChange24h,
                        description = coinDetails.description,
                        logo = coinDetails.logo,
                        volume24 = coins.volume24,
                    )
                )
            } catch (e: Throwable) {
                e.printStackTrace()
                _uiState.value = DetailsViewState.Error
            }
        }
    }
}

sealed interface DetailsViewState {
    object Loading : DetailsViewState
    object Error : DetailsViewState
    data class Success(val coin: CoinDetailsModel) : DetailsViewState
}