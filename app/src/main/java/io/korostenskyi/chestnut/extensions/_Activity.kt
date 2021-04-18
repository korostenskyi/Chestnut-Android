package io.korostenskyi.chestnut.extensions

import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.LifecycleOwner

inline fun ComponentActivity.overrideBackPress(
    owner: LifecycleOwner,
    crossinline block: () -> Unit
) {
    onBackPressedDispatcher.addCallback(owner, object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            block()
        }
    })
}

