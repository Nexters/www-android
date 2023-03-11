package com.promiseeight.www.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.promiseeight.www.domain.usecase.auth.GetVersionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getVersionUseCase: GetVersionUseCase
) : ViewModel() {

    private val _recentVersion = MutableStateFlow<String>("")
    val recentVersion : StateFlow<String> get() = _recentVersion

    fun getRecentVersion() {
        try {
            viewModelScope.launch {
                getVersionUseCase().collectLatest {
                    it.onSuccess {
                        _recentVersion.emit(it)
                    }.onFailure {
                        Timber.e(it)
                    }
                }
            }
        } catch (e : Exception){
            Timber.e(e)
        }
    }
}