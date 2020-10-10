package io.korostenskyi.chestnut.presentation.base.ui

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber

abstract class BaseActivity(
    @LayoutRes layoutId: Int
) : AppCompatActivity(layoutId) {

    private var backPressFunction: (() -> Unit)? = null

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(LIFECYCLE_TAG).d("${javaClass.simpleName} onCreate")
    }

    @CallSuper
    override fun onDestroy() {
        Timber.tag(LIFECYCLE_TAG).d("${javaClass.simpleName} onDestroy")
        super.onDestroy()
    }

    override fun onBackPressed() {
        backPressFunction?.invoke() ?: super.onBackPressed()
    }

    fun overrideBackPress(back: () -> Unit) {
        backPressFunction = back
    }

    fun clearOverriddenBackPress() {
        backPressFunction = null
    }

    companion object {
        private const val LIFECYCLE_TAG = "LifecycleEvent"
    }
}
