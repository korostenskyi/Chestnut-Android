package io.korostenskyi.chestnut.util.delegate

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewbinding.ViewBinding
import kotlin.reflect.KProperty

class ViewBindingDelegate<B : ViewBinding>(
    private val fragment: Fragment,
    private val binder: (View) -> B
) : LifecycleObserver {

    private var binding: B? = null

    operator fun getValue(thisRef: Fragment, property: KProperty<*>): B {
        if (binding == null) fragment.viewLifecycleOwner.lifecycle.addObserver(this)
        return binding ?: binder(thisRef.requireView()).also { binding = it }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        binding = null
        fragment.viewLifecycleOwner.lifecycle.removeObserver(this)
    }
}
