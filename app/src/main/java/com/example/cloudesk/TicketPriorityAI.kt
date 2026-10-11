package com.example.cloudesk

/**
 * Lightweight, on-device "AI" ticket triage engine.
 *
 * This app has no backend or API key wired up, so instead of faking a call to a
 * cloud model, this runs a transparent keyword + heuristic scoring pass over the
 * ticket text entirely on-device (fast, works offline, no data leaves the phone).
 *
 * If/when a real backend is added, this is the single place to swap in an actual
 * model call (e.g. POST the title/description to a classification endpoint and
 * map its response into a PriorityResult) without touching any UI code.
 */
object TicketPriorityAI {

    enum class Priority(val label: String, val displayName: String) {
        LOW("low", "Low"),
        MEDIUM("medium", "Medium"),
        HIGH("high", "High"),
        CRITICAL("critical", "Critical")
    }

    data class PriorityResult(
        val priority: Priority,
        val confidence: Int, // 0-100
        val matchedSignals: List<String>,
        val rawScore: Int = 0
    )

    /** Stable ordering for sorting a ticket queue, highest urgency first. */
    fun priorityRank(priority: String): Int = when (priority.lowercase()) {
        "critical" -> 3
        "high" -> 2
        "medium" -> 1
        "low" -> 0
        else -> 1
    }

    private val criticalTerms = listOf(
        "outage", "down", "cannot access", "can't access", "not working at all",
        "data loss", "lost data", "breach", "hacked", "security", "leak",
        "all users", "entire team", "production", "urgent", "asap", "emergency",
        "crashed", "crashing", "unusable", "locked out"
    )

    private val highTerms = listOf(
        "error", "crash", "broken", "failing", "fails", "cannot log in", "can't log in",
        "server error", "500", "timeout", "slow", "blocked", "unable to", "not able to",
        "important", "deadline"
    )

    private val mediumTerms = listOf(
        "issue", "bug", "problem", "glitch", "unexpected", "incorrect", "wrong",
        "missing", "not showing", "delay"
    )

    private val lowTerms = listOf(
        "question", "request", "suggestion", "feedback", "cosmetic", "typo",
        "minor", "when convenient", "no rush", "nice to have", "how do i", "how to"
    )

    private val urgentCategories = setOf("network", "account/access", "auth", "backend")

    fun analyze(title: String, description: String, category: String): PriorityResult {
        val text = "$title $description".lowercase().trim()
        val matched = mutableListOf<String>()
        var score = 0

        criticalTerms.forEach { term -> if (text.contains(term)) { score += 4; matched += term } }
        highTerms.forEach { term -> if (text.contains(term)) { score += 2; matched += term } }
        mediumTerms.forEach { term -> if (text.contains(term)) { score += 1; matched += term } }
        lowTerms.forEach { term -> if (text.contains(term)) { score -= 2; matched += term } }

        // Category weighting: access/network/auth issues tend to block work entirely.
        if (category.lowercase() in urgentCategories) score += 2

        // Shouting / exclamation marks are a weak urgency signal.
        val exclamations = text.count { it == '!' }
        if (exclamations >= 2) { score += 1; matched += "! ! (urgent tone)" }

        val upperRatio = if (title.isNotEmpty()) title.count { it.isUpperCase() }.toFloat() / title.length else 0f
        if (title.length > 6 && upperRatio > 0.6f) { score += 1; matched += "ALL CAPS TITLE" }

        val priority = when {
            score >= 6 -> Priority.CRITICAL
            score >= 3 -> Priority.HIGH
            score >= 1 -> Priority.MEDIUM
            else -> Priority.LOW
        }

        // Confidence grows with how many distinct signals fired, caps at 96 to stay honest
        // that this is a heuristic, not a guarantee.
        val confidence = (55 + matched.size * 8).coerceAtMost(96)

        return PriorityResult(priority, confidence, matched.distinct(), score)
    }
}
