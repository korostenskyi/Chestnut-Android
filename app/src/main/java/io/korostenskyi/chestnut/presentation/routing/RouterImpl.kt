package io.korostenskyi.chestnut.presentation.routing

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import io.korostenskyi.chestnut.R
import io.korostenskyi.chestnut.presentation.screen.popular.PopularFragmentDirections
import javax.inject.Inject

class RouterImpl @Inject constructor(
    private val navController: NavController
) : Router {

    private val defaultNavOptionsBuilder: NavOptions.Builder
        get() = NavOptions.Builder()
                .setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
                .setPopExitAnim(R.anim.nav_default_pop_exit_anim)
                .setEnterAnim(R.anim.nav_default_enter_anim)
                .setExitAnim(R.anim.nav_default_exit_anim)

    override fun navigate(navigator: (Router) -> Unit) {
        navigator(this)
    }

    override fun back() {
        navController.navigateUp()
    }

    override fun fromPopularToMovieDetails(movieId: Int) {
        navigate(PopularFragmentDirections.popularToMovieDetails(movieId))
    }

    private fun navigate(
        destinationId: Int,
        popStrategy: PopStrategy = PopStrategy.NONE,
        bundle: Bundle? = null
    ) {
        val navOptions = defaultNavOptionsBuilder
                .setPopStrategy(popStrategy)
                .build()
        navController.navigate(destinationId, bundle, navOptions)
    }

    private fun navigate(direction: NavDirections, popStrategy: PopStrategy = PopStrategy.NONE) {
        val navOptions = defaultNavOptionsBuilder
                .setPopStrategy(popStrategy)
                .build()
        navController.navigate(direction, navOptions)
    }

    private fun NavOptions.Builder.setPopStrategy(popStrategy: PopStrategy): NavOptions.Builder {
        return when (popStrategy) {
            PopStrategy.NONE -> this
            PopStrategy.LATEST -> setPopLatest()
            PopStrategy.ALL -> setPopAll()
        }
    }

    private fun NavOptions.Builder.setPopLatest(): NavOptions.Builder {
        return navController.currentDestination
                ?.let { setPopUpTo(it.id, true) }
                ?: this
    }

    private fun NavOptions.Builder.setPopAll(): NavOptions.Builder {
        return setPopUpTo(navController.graph.id, false)
    }
}