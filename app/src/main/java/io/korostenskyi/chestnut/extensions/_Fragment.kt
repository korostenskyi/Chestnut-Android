package io.korostenskyi.chestnut.extensions

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import io.korostenskyi.chestnut.presentation.routing.Router
import io.korostenskyi.chestnut.presentation.routing.RouterImpl
import io.korostenskyi.chestnut.util.ViewBindingDelegate

fun <B : ViewBinding> Fragment.viewBindings(binder: (View) -> B): ViewBindingDelegate<B> {
    return ViewBindingDelegate(this, binder)
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun Fragment.hideKeyboard() {
    requireView().hideKeyboard()
}

fun Fragment.findRouter(): Router {
    return RouterImpl(findNavController())
}
