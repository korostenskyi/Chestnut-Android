package io.korostenskyi.chestnut.presentation.base.viewModel

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import timber.log.Timber

abstract class BaseViewModel : ViewModel() {

    init {
        Timber.tag(TAG).d("${javaClass.simpleName} init")
    }

    @CallSuper
    open fun onDestroy() {
        Timber.tag(TAG).d("${javaClass.simpleName} onDestroy")
    }

    @CallSuper
    override fun onCleared() {
        Timber.tag(TAG).d("${javaClass.simpleName} onCleared")
        super.onCleared()
    }

    companion object {
        private const val TAG = "ViewModelEvent"
    }
}
