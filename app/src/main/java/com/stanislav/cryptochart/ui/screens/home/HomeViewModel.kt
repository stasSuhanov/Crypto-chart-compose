package com.stanislav.cryptochart.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stanislav.cryptochart.data.repository.CoinsRepository
import com.stanislav.cryptochart.domain.entity.CoinEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val coinsRepository: CoinsRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<HomeViewState> = MutableStateFlow(HomeViewState.Loading)
    val uiState: StateFlow<HomeViewState> = _uiState

    init {
        fetchCoins()
    }

    private fun fetchCoins() {
        _uiState.value = HomeViewState.Loading
        viewModelScope.launch {
            try {
                val coins = coinsRepository.fetchCoins()
                if (coins.isNotEmpty()) {
                    _uiState.value = HomeViewState.Success(coins)
                } else {
                    _uiState.value = HomeViewState.Error
                }
            } catch (e: Throwable) {
                e.printStackTrace()
                _uiState.value = HomeViewState.Error
            }
        }
    }
}

sealed interface HomeViewState {
    object Loading : HomeViewState
    object Error : HomeViewState
    data class Success(val coins: List<CoinEntity>) : HomeViewState
}