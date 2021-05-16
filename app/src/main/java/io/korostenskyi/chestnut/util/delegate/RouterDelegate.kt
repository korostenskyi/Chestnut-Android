package io.korostenskyi.chestnut.util.delegate

import androidx.fragment.app.Fragment
import io.korostenskyi.chestnut.extensions.findRouter
import io.korostenskyi.chestnut.presentation.routing.Router
import kotlin.reflect.KProperty

class RouterDelegate {

    lateinit var router: Router

    operator fun getValue(thisRef: Fragment, property: KProperty<*>): Router {
        return if (::router.isInitialized) router else thisRef.findRouter().also { router = it }
    }
}