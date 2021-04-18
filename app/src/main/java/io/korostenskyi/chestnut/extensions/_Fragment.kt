package io.korostenskyi.chestnut.extensions

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import io.korostenskyi.chestnut.util.ViewBindingDelegate
import kotlinx.coroutines.launch

fun <B : ViewBinding> Fragment.viewBindings(binder: (View) -> B): ViewBindingDelegate<B> {
    return ViewBindingDelegate(this, binder)
}

inline fun Fragment.launch(
    crossinline block: suspend () -> Unit
) {
    lifecycleScope.launchWhenStarted {
        block()
    }
}
