package io.korostenskyi.chestnut.extensions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

inline fun ViewModel.launch(crossinline block: suspend () -> Unit) {
    viewModelScope.launch {
        block()
    }
}
