package com.example.cloudesk

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.card.MaterialCardView

/** Searchable knowledge base: tap a question to expand the answer. */
class FaqActivity : BaseActivity() {

    private data class Article(val title: String, val body: String)

    private lateinit var container: LinearLayout
    private lateinit var articles: List<Article>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faq)

        findViewById<ImageButton>(R.id.backButton).setOnClickListener { finish() }
        container = findViewById(R.id.faqContainer)

        articles = listOf(
            Article(getString(R.string.faq_1_title), getString(R.string.faq_1_body)),
            Article(getString(R.string.faq_2_title), getString(R.string.faq_2_body)),
            Article(getString(R.string.faq_3_title), getString(R.string.faq_3_body)),
            Article(getString(R.string.faq_4_title), getString(R.string.faq_4_body)),
            Article(getString(R.string.faq_5_title), getString(R.string.faq_5_body)),
            Article(getString(R.string.faq_6_title), getString(R.string.faq_6_body))
        )

        findViewById<EditText>(R.id.faqSearchInput).addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                render(s?.toString()?.trim()?.lowercase() ?: "")
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        render("")
    }

    private fun render(query: String) {
        container.removeAllViews()
        val matches = articles.filter {
            query.isEmpty() || it.title.lowercase().contains(query) || it.body.lowercase().contains(query)
        }
        if (matches.isEmpty()) {
            container.addView(TextView(this).apply {
                text = getString(R.string.faq_no_results)
                setTextColor(getColor(R.color.text_muted))
                textSize = 14f
            })
            return
        }
        val pad = (14 * resources.displayMetrics.density).toInt()
        val gap = (10 * resources.displayMetrics.density).toInt()
        matches.forEach { article ->
            val card = MaterialCardView(this).apply {
                setCardBackgroundColor(getColor(R.color.glass_bg))
                radius = 12 * resources.displayMetrics.density
                strokeColor = getColor(R.color.glass_border)
                strokeWidth = 1
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply { bottomMargin = gap }
            }
            val inner = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(pad, pad, pad, pad)
            }
            val title = TextView(this).apply {
                text = article.title
                setTextColor(getColor(R.color.text_main))
                textSize = 15f
                setTypeface(typeface, android.graphics.Typeface.BOLD)
            }
            val body = TextView(this).apply {
                text = article.body
                setTextColor(getColor(R.color.text_muted))
                textSize = 14f
                setPadding(0, gap / 2, 0, 0)
                visibility = if (query.isNotEmpty()) View.VISIBLE else View.GONE
            }
            card.setOnClickListener {
                body.visibility = if (body.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            }
            inner.addView(title)
            inner.addView(body)
            card.addView(inner)
            container.addView(card)
        }
    }
}
