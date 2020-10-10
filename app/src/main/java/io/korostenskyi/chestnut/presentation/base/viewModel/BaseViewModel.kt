package io.korostenskyi.chestnut.presentation.base.viewModel

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import timber.log.Timber

abstract class BaseViewModel : ViewModel() {

    init {
        Timber.tag(TAG).d("${javaClass.simpleName} init")
    }

    @CallSuper
    open fun onCreate() {}

    @CallSuper
    open fun onViewCreated() {}

    @CallSuper
    open fun onDestroyView() {}

    @CallSuper
    open fun onDestroy() {}

    @CallSuper
    override fun onCleared() {
        Timber.tag(TAG).d("${javaClass.simpleName} onCleared")
        super.onCleared()
    }

    companion object {
        private const val TAG = "ViewModelEvent"
    }
}
