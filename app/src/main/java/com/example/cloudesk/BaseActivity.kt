package com.example.cloudesk

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.TooltipCompat

/**
 * All activities extend this so the chosen language (LocaleHelper) and theme (ThemeHelper)
 * are applied consistently everywhere. It also gives every icon button a text label:
 * a long-press tooltip for sighted users and a content description for TalkBack.
 */
abstract class BaseActivity : AppCompatActivity() {
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LocaleHelper.wrapContext(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        ThemeHelper.applyTheme(this)
        super.onCreate(savedInstanceState)
    }

    override fun onContentChanged() {
        super.onContentChanged()
        labelIconButtons(window.decorView)
    }

    private fun labelIconButtons(v: View) {
        if (v is ImageButton) {
            if (v.contentDescription.isNullOrBlank()) v.contentDescription = humanize(v)
            TooltipCompat.setTooltipText(v, v.contentDescription)
        } else if (v is ViewGroup) {
            for (i in 0 until v.childCount) labelIconButtons(v.getChildAt(i))
        }
    }

    /** backButton -> "Back", sendButton -> "Send" (id name without "Button"/"Btn"). */
    private fun humanize(v: View): String {
        val name = if (v.id != View.NO_ID) runCatching { resources.getResourceEntryName(v.id) }.getOrNull() else null
        val base = (name ?: "button").replace(Regex("(Button|Btn|Icon)$"), "")
        return base.replace(Regex("([a-z])([A-Z])"), "$1 $2").replaceFirstChar { it.uppercase() }.ifBlank { "Button" }
    }
}
