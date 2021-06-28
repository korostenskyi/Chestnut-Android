package io.korostenskyi.chestnut.util.ui.impl

import android.content.Context
import android.content.Intent
import dagger.hilt.android.qualifiers.ApplicationContext
import io.korostenskyi.chestnut.util.ui.IntentActionUtil
import javax.inject.Inject

class IntentActionUtilImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : IntentActionUtil {

    override fun share(text: String) {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            type = "text/plain"
        }
        context.startActivity(intent)
    }
}
