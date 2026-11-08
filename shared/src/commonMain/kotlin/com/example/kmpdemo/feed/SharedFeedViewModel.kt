package com.example.kmpdemo.feed

import com.example.kmpdemo.platform.BleScanner
import com.example.kmpdemo.platform.BluetoothSummary
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SharedFeedUiState(
    val events: List<GitHubEventRow> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val bluetooth: BluetoothSummary? = null,
    val bleError: String? = null,
    val isBleRefreshing: Boolean = false,
    /** 每次「重读蓝牙」成功后递增，便于 iOS 等结果不变时仍有可见反馈。 */
    val bleReadGeneration: Int = 0,
)

class SharedFeedViewModel(
    private val repository: GitHubEventsRepository =
        createGitHubEventsRepository(createAppHttpClient()),
    private val bleScanner: BleScanner = BleScanner(),
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val _uiState = MutableStateFlow(SharedFeedUiState())
    val uiState: StateFlow<SharedFeedUiState> = _uiState.asStateFlow()

    fun loadFeed() {
        scope.launch {
            _uiState.update {
                it.copy(isLoading = true, errorMessage = null)
            }
            runCatching { repository.fetchPublicFeed(30) }
                .onSuccess { rows ->
                    _uiState.update {
                        it.copy(events = rows, isLoading = false, errorMessage = null)
                    }
                }
                .onFailure { e ->
                    _uiState.update {
                        it.copy(isLoading = false, errorMessage = e.message ?: e.toString())
                    }
                }
        }
    }

    fun refreshBleSummary(userInitiated: Boolean = true) {
        scope.launch {
            _uiState.update { it.copy(isBleRefreshing = true, bleError = null) }
            runCatching { bleScanner.readSummary() }
                .onSuccess { summary ->
                    _uiState.update {
                        it.copy(
                            bluetooth = summary,
                            bleError = null,
                            isBleRefreshing = false,
                            bleReadGeneration = if (userInitiated) {
                                it.bleReadGeneration + 1
                            } else {
                                it.bleReadGeneration
                            },
                        )
                    }
                }
                .onFailure { e ->
                    _uiState.update {
                        it.copy(
                            isBleRefreshing = false,
                            bleError = e.message ?: e.toString(),
                        )
                    }
                }
        }
    }

    fun onCleared() {
        scope.cancel()
    }
}
